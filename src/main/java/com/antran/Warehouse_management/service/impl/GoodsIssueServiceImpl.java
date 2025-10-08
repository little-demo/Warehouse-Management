package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueDetailRequest;
import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueRequest;
import com.antran.Warehouse_management.dto.request.GoodsReceipt.CancelGoodsDetailRequest;
import com.antran.Warehouse_management.dto.request.GoodsReceipt.CancelGoodsRequest;
import com.antran.Warehouse_management.dto.response.GoodsIssueResponse;
import com.antran.Warehouse_management.entity.*;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.GoodsIssueMapper;
import com.antran.Warehouse_management.repository.GoodsIssueDetailRepository;
import com.antran.Warehouse_management.repository.GoodsIssueRepository;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.repository.UnitConversionRepository;
import com.antran.Warehouse_management.service.CustomerService;
import com.antran.Warehouse_management.service.GoodsIssueService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
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

    @Override
    @Transactional
    public GoodsIssueResponse createGoodsIssue(GoodsIssueRequest request) {
        if (goodsIssueRepository.existsByIssueCode(request.getIssueCode())) {
            throw new AppException(ErrorCode.ISSUE_CODE_ALREADY_EXISTS);
        }

        User user = userService.findUserById(request.getCreatedById());
        Customer customer = customerService.findCustomerById(request.getCustomerId());

        GoodsIssue goodsIssue = GoodsIssueMapper.toEntity(request, user, customer);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (GoodsIssueDetailRequest detailReq : request.getDetails()) {

            Product product = productService.findProductById(detailReq.getProductId());
            UnitConversion conversion = unitConversionRepository.findById(detailReq.getUnitConversionId())
                    .orElse(null);

            //Tính tỷ lệ quy đổi sang đơn vị gốc
            BigDecimal ratio = (conversion != null)
                    ? conversion.getRatioToBase()
                    : BigDecimal.ONE;

            BigDecimal baseQuantityToIssue = detailReq.getQuantity().multiply(ratio);
            BigDecimal remainingToIssue = baseQuantityToIssue;

            //Lấy danh sách batch theo FIFO
            List<InventoryBatch> availableBatches = inventoryBatchRepository
                    .findByProductIdOrderByCreatedAtAsc(product.getId());

            for (InventoryBatch batch : availableBatches) {
                if (remainingToIssue.compareTo(BigDecimal.ZERO) <= 0) break;

                BigDecimal available = batch.getRemainingQuantity();

                if (available.compareTo(BigDecimal.ZERO) <= 0) continue;

                //Xác định số lượng xuất từ batch này (đơn vị gốc)
                BigDecimal issueFromThisBatch = available.min(remainingToIssue);

                //Quy đổi lại về đơn vị người nhập
                BigDecimal displayQuantity = issueFromThisBatch.divide(ratio, 2, RoundingMode.HALF_UP);

                //Cập nhật tồn kho batch
                batch.setRemainingQuantity(available.subtract(issueFromThisBatch));
                inventoryBatchRepository.save(batch);

                //Tính tiền xuất
                BigDecimal totalPrice = detailReq.getUnitPrice().multiply(displayQuantity);

                //Tạo chi tiết phiếu xuất
                GoodsIssueDetail detail = GoodsIssueDetail.builder()
                        .goodsIssue(goodsIssue)
                        .inventoryBatch(batch)
                        .unitConversion(conversion)
                        .quantity(displayQuantity)   // theo đơn vị nhập
                        .unitPrice(detailReq.getUnitPrice())
                        .totalPrice(totalPrice)
                        .build();

                goodsIssue.getDetails().add(detail);

                totalAmount = totalAmount.add(totalPrice);
                remainingToIssue = remainingToIssue.subtract(issueFromThisBatch);
            }

            //Kiểm tra nếu vẫn còn số lượng chưa xuất hết
            if (remainingToIssue.compareTo(BigDecimal.ZERO) > 0) {
                throw new AppException(ErrorCode.NOT_ENOUGH_STOCK);
            }
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
    public void deleteGoodsIssue(int id) {
        GoodsIssue goodsIssue = findGoodsIssueById(id);

        for (GoodsIssueDetail detail : goodsIssue.getDetails()) {
            InventoryBatch batch = detail.getInventoryBatch();
            BigDecimal returnedQty = detail.getQuantity();

            batch.setRemainingQuantity(batch.getRemainingQuantity().add(returnedQty));
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
