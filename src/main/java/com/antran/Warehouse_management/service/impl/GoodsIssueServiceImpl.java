package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueDetailRequest;
import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueRequest;
import com.antran.Warehouse_management.dto.request.GoodsIssue.CancelGoodsDetailRequest;
import com.antran.Warehouse_management.dto.request.GoodsIssue.CancelGoodsRequest;
import com.antran.Warehouse_management.dto.response.GoodsIssueResponse;
import com.antran.Warehouse_management.entity.*;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.GoodsIssueMapper;
import com.antran.Warehouse_management.repository.GoodsIssueDetailRepository;
import com.antran.Warehouse_management.repository.GoodsIssueRepository;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.repository.UnitConversionRepository;
import com.antran.Warehouse_management.service.GoodsIssueService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GoodsIssueServiceImpl implements GoodsIssueService {
    GoodsIssueRepository goodsIssueRepository;
    InventoryBatchRepository inventoryBatchRepository;
    UnitConversionRepository unitConversionRepository;
    GoodsIssueDetailRepository goodsIssueDetailRepository;

    CustomerServiceImpl customerService;
    UserServiceImpl userService;
    ProductServiceImpl productService;
    PartnerServiceImpl partnerService;

    @Override
    @Transactional
    public GoodsIssueResponse createGoodsIssue(GoodsIssueRequest request) {
        if (goodsIssueRepository.existsByIssueCode(request.getIssueCode())) {
            throw new AppException(ErrorCode.ISSUE_CODE_ALREADY_EXISTS);
        }

        User user = userService.findUserById(request.getCreatedById());
        Partner customer = partnerService.findPartnerById(request.getCustomerId());

        GoodsIssue goodsIssue = GoodsIssueMapper.toEntity(request, user, customer);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (GoodsIssueDetailRequest detailReq : request.getDetails()) {
            Product product = productService.findProductById(detailReq.getProductId());
            InventoryBatch batch = inventoryBatchRepository.findById(detailReq.getInventoryBatchId())
                    .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

            if (!(batch.getProduct().getId() == product.getId())) {
                throw new AppException(ErrorCode.BATCH_PRODUCT_MISMATCH);
            }

            UnitConversion conversion = null;
            BigDecimal ratio = BigDecimal.ONE;

            if (detailReq.getUnitConversionId() != null) {
                conversion = unitConversionRepository.findById(detailReq.getUnitConversionId())
                        .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
                ratio = conversion.getRatioToBase();
            }

            // Quy đổi sang đơn vị gốc để trừ kho
            BigDecimal baseQuantityToIssue = detailReq.getQuantity().multiply(ratio);

            if (batch.getRemainingQuantity().compareTo(baseQuantityToIssue) < 0) {
                throw new AppException(ErrorCode.NOT_ENOUGH_STOCK);
            }

            // --- Tính giá xuất ---
            BigDecimal baseUnitPrice = batch.getUnitCost(); // Giá theo đơn vị gốc (vd: theo chai)
            BigDecimal displayUnitPriceRaw = baseUnitPrice.multiply(ratio); // Giá theo đơn vị hiển thị (vd: thùng)

            // Làm tròn tổng tiền trước, để ra số đẹp (ví dụ 200,000)
            BigDecimal rawTotal = displayUnitPriceRaw.multiply(detailReq.getQuantity());
            BigDecimal roundedTotalPrice = rawTotal.setScale(0, RoundingMode.HALF_UP); // Làm tròn đến đồng

            // Tính lại đơn giá hiển thị từ tổng tiền đã làm tròn
            BigDecimal roundedUnitPrice = roundedTotalPrice.divide(detailReq.getQuantity(), 2, RoundingMode.HALF_UP);

            // Cập nhật tồn kho
            batch.setRemainingQuantity(batch.getRemainingQuantity().subtract(baseQuantityToIssue));
            inventoryBatchRepository.save(batch);

            // Ghi chi tiết
            GoodsIssueDetail detail = GoodsIssueDetail.builder()
                    .goodsIssue(goodsIssue)
                    .inventoryBatch(batch)
                    .unitConversion(conversion)
                    .quantity(detailReq.getQuantity())
                    .unitPrice(roundedUnitPrice)        // Giá hiển thị (đã làm tròn)
                    .totalPrice(roundedTotalPrice)      // Tổng giá (đã làm tròn)
                    .build();

            goodsIssue.getDetails().add(detail);
            totalAmount = totalAmount.add(roundedTotalPrice);
        }

        goodsIssue.setTotalAmount(totalAmount);

        GoodsIssue saved = goodsIssueRepository.save(goodsIssue);
        return GoodsIssueMapper.toResponse(saved);
    }

    @Override
    public GoodsIssueResponse getGoodsIssueById(int id) {
        return GoodsIssueMapper.toResponse(findGoodsIssueById(id));
    }

    @Override
    public List<GoodsIssueResponse> getAllGoodsIssues() {
        return goodsIssueRepository.findAll().stream()
                .map(GoodsIssueMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteGoodsIssue(int id) {
        GoodsIssue goodsIssue = findGoodsIssueById(id);

        for (GoodsIssueDetail detail : goodsIssue.getDetails()) {
            InventoryBatch batch = detail.getInventoryBatch();
            UnitConversion conversion = detail.getUnitConversion();

            // Nếu conversion == null => dùng base unit (tỷ lệ 1)
            BigDecimal ratio = (conversion != null) ? conversion.getRatioToBase() : BigDecimal.ONE;

            // Quy đổi số lượng về đơn vị cơ bản
            BigDecimal qtyInBase = detail.getQuantity().multiply(ratio);

            log.info("before restore {} (base unit) to batch {}", qtyInBase, batch.getBatchCode());

            // Cộng lại vào số lượng tồn kho
            batch.setRemainingQuantity(batch.getRemainingQuantity().add(qtyInBase));

            log.info("after restore {} (base unit) to batch {}", qtyInBase, batch.getBatchCode());

            inventoryBatchRepository.save(batch);
        }

        goodsIssueRepository.delete(goodsIssue);
    }

    @Override
    @Transactional
    public GoodsIssueResponse cancelGoodsIssue(CancelGoodsRequest request) {
        if (goodsIssueRepository.existsByIssueCode(request.getIssueCode())) {
            throw new AppException(ErrorCode.ISSUE_CODE_ALREADY_EXISTS);
        }
        User user = userService.findUserById(request.getCreatedById());

        GoodsIssue cancelIssue = GoodsIssueMapper.toEntity(request, user);

        goodsIssueRepository.save(cancelIssue);

        BigDecimal totalLossValue = BigDecimal.ZERO;

        for (CancelGoodsDetailRequest detailReq : request.getDetails()) {
            InventoryBatch batch = inventoryBatchRepository.findById(detailReq.getInventoryBatchId())
                    .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

            UnitConversion conversion = unitConversionRepository.findById(detailReq.getUnitConversionId())
                    .orElse(null);

            BigDecimal ratio = (conversion != null)
                    ? conversion.getRatioToBase()
                    : BigDecimal.ONE;

            //Quy đổi sang đơn vị gốc để kiểm tồn kho
            BigDecimal baseQuantity = detailReq.getQuantity().multiply(ratio);

            if (batch.getRemainingQuantity().compareTo(baseQuantity) < 0) {
                throw new AppException(ErrorCode.NOT_ENOUGH_STOCK);
            }

            //Giảm tồn kho
            batch.setRemainingQuantity(batch.getRemainingQuantity().subtract(baseQuantity));
            inventoryBatchRepository.save(batch);

            //Tính tổn thất (giá vốn × số lượng)
            BigDecimal lossValue = detailReq.getUnitPrice().multiply(detailReq.getQuantity());
            totalLossValue = totalLossValue.add(lossValue);

            //Tạo chi tiết phiếu hủy
            GoodsIssueDetail detail = GoodsIssueDetail.builder()
                    .goodsIssue(cancelIssue)
                    .inventoryBatch(batch)
                    .unitConversion(conversion)
                    .quantity(detailReq.getQuantity())
                    .unitPrice(detailReq.getUnitPrice())   // giá vốn hoặc tổn thất
                    .totalPrice(lossValue)
                    .build();
            cancelIssue.getDetails().add(detail);
//            goodsIssueDetailRepository.save(detail);
            cancelIssue.getDetails().add(detail);
        }

        cancelIssue.setTotalAmount(totalLossValue);
        goodsIssueRepository.save(cancelIssue);

        return GoodsIssueMapper.toResponse(cancelIssue);
    }

    GoodsIssue findGoodsIssueById(int id) {
        return goodsIssueRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GOODS_ISSUE_NOT_FOUND));
    }
}
