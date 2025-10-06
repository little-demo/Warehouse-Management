package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptDetailRequest;
import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptRequest;
import com.antran.Warehouse_management.dto.response.GoodsReceiptResponse;
import com.antran.Warehouse_management.entity.*;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.GoodsReceiptDetailMapper;
import com.antran.Warehouse_management.mapper.GoodsReceiptMapper;
import com.antran.Warehouse_management.repository.GoodsReceiptDetailRepository;
import com.antran.Warehouse_management.repository.GoodsReceiptRepository;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.service.GoodsReceiptService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GoodsReceiptServiceImpl implements GoodsReceiptService {
    GoodsReceiptRepository goodsReceiptRepository;
    GoodsReceiptDetailRepository goodsReceiptDetailRepository;
    InventoryBatchRepository inventoryBatchRepository;

    UserServiceImpl userService;
    SupplierServiceImpl supplierService;
    LocationServiceImpl locationService;
    ProductServiceImpl productService;

    @Override
    @Transactional
    public GoodsReceiptResponse createGoodsReceipt(GoodsReceiptRequest request) {
        try {
            if (goodsReceiptRepository.existsByReceiptCode(request.getReceiptCode())) {
                throw new AppException(ErrorCode.RECEIPT_CODE_ALREADY_EXISTS);
            }
            // 1️⃣ Tìm thông tin user & supplier
            User user = userService.findUserById(request.getCreatedById());
            Supplier supplier = supplierService.findSupplierById(request.getSupplierId());

            // 2️⃣ Map từ request sang entity phiếu nhập
            GoodsReceipt goodsReceipt = GoodsReceiptMapper.toEntity(request);
            goodsReceipt.setCreatedBy(user);
            goodsReceipt.setSupplier(supplier);

            // 3️⃣ Map danh sách chi tiết (không còn location)
            List<GoodsReceiptDetail> details = request.getDetails().stream()
                    .map(detailRequest -> {
                        GoodsReceiptDetail detail = GoodsReceiptDetailMapper.toEntity(detailRequest);
                        detail.setGoodsReceipt(goodsReceipt);
                        detail.setProduct(productService.findProductById(detailRequest.getProductId()));

                        // Tính totalPrice
                        detail.setTotalPrice(detail.getUnitPrice().multiply(detail.getQuantity()));
                        return detail;
                    })
                    .toList();

            goodsReceipt.setDetails(details);

            // 4️⃣ Tính tổng tiền phiếu nhập
            BigDecimal totalAmount = details.stream()
                    .map(GoodsReceiptDetail::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            goodsReceipt.setTotalAmount(totalAmount);

            // 5️⃣ Lưu phiếu nhập & chi tiết
            GoodsReceipt savedReceipt = goodsReceiptRepository.save(goodsReceipt);

            // 6️⃣ Tạo các lô hàng (InventoryBatch) tương ứng cho từng chi tiết
            List<InventoryBatch> batches = new ArrayList<>();
            int index = 1;

            for (int i = 0; i < request.getDetails().size(); i++) {
                GoodsReceiptDetailRequest detailReq = request.getDetails().get(i);
                GoodsReceiptDetail savedDetail = savedReceipt.getDetails().get(i);

                InventoryBatch batch = InventoryBatch.builder()
                        .batchCode(savedReceipt.getReceiptCode() + "-" + index++) // VD: PN001-1
                        .product(savedDetail.getProduct())
                        .goodsReceiptDetail(savedDetail)
                        .location(locationService.findLocationById(detailReq.getLocationId()))
                        .initialQuantity(savedDetail.getQuantity())
                        .remainingQuantity(savedDetail.getQuantity())
                        .unitCost(savedDetail.getUnitPrice())
                        .build();

                batches.add(batch);
            }

            inventoryBatchRepository.saveAll(batches);

            return GoodsReceiptMapper.toResponse(savedReceipt);

        } catch (Exception e) {
            log.error("Error when creating goods receipt: {}", e.getMessage(), e);
            throw new RuntimeException("Tạo phiếu nhập thất bại!");
        }
    }

    @Override
    public GoodsReceiptResponse getGoodsReceiptById(int id) {
        return GoodsReceiptMapper.toResponse(findGoodsReceiptById(id));
    }

    @Override
    public List<GoodsReceiptResponse> getAllGoodsReceipts() {
        return goodsReceiptRepository.findAll().stream()
                .map(GoodsReceiptMapper::toResponse)
                .toList();
    }

    @Override
    public GoodsReceiptResponse updateGoodsReceipt(int id, GoodsReceiptRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void deleteGoodsReceipt(int id) {
        GoodsReceipt goodsReceipt = findGoodsReceiptById(id);
        List<InventoryBatch> batches = inventoryBatchRepository.findByGoodsReceiptId(id);

        // Kiểm tra còn nguyên hàng không
        boolean hasMovement = batches.stream()
                .anyMatch(batch -> batch.getRemainingQuantity().compareTo(batch.getInitialQuantity()) < 0);

        if (hasMovement) {
            throw new AppException(ErrorCode.GOODS_RECEIPT_CANNOT_BE_DELETED);
        }

        // Xóa batch trước
        inventoryBatchRepository.deleteAll(batches);

        // Xóa phiếu nhập (và chi tiết)
        goodsReceiptRepository.delete(goodsReceipt);
    }

    GoodsReceipt findGoodsReceiptById(int id) {
        return goodsReceiptRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GOODS_RECEIPT_NOT_FOUND));
    }
}
