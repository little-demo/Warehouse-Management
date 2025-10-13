package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptDetailRequest;
import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptRequest;
import com.antran.Warehouse_management.dto.response.GoodsReceiptResponse;
import com.antran.Warehouse_management.entity.*;
import com.antran.Warehouse_management.enums.DebtStatus;
import com.antran.Warehouse_management.enums.DebtType;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.GoodsReceiptDetailMapper;
import com.antran.Warehouse_management.mapper.GoodsReceiptMapper;
import com.antran.Warehouse_management.repository.GoodsReceiptRepository;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.repository.PartnerDebtRepository;
import com.antran.Warehouse_management.repository.UnitConversionRepository;
import com.antran.Warehouse_management.service.GoodsReceiptService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GoodsReceiptServiceImpl implements GoodsReceiptService {
    GoodsReceiptRepository goodsReceiptRepository;
    InventoryBatchRepository inventoryBatchRepository;
    UnitConversionRepository unitConversionRepository;
    PartnerDebtRepository partnerDebtRepository;

    UserServiceImpl userService;
    PartnerServiceImpl partnerService;
    LocationServiceImpl locationService;
    ProductServiceImpl productService;

    @Override
    @Transactional
    public GoodsReceiptResponse createGoodsReceipt(GoodsReceiptRequest request) {
        // 1️⃣ Kiểm tra trùng mã phiếu
        if (goodsReceiptRepository.existsByReceiptCode(request.getReceiptCode())) {
            throw new AppException(ErrorCode.RECEIPT_CODE_ALREADY_EXISTS);
        }

        // 2️⃣ Lấy thông tin người tạo & nhà cung cấp
        User user = userService.findUserById(request.getCreatedById());
        Partner partner = partnerService.findPartnerById(request.getPartnerId());
//        Supplier supplier = supplierService.findSupplierById(request.getSupplierId());

        // 3️⃣ Map sang entity phiếu nhập
        GoodsReceipt goodsReceipt = GoodsReceiptMapper.toEntity(request);
        goodsReceipt.setCreatedBy(user);
        goodsReceipt.setPartner(partner);

        // 4️⃣ Danh sách chi tiết phiếu nhập
        List<GoodsReceiptDetail> details = new ArrayList<>();
        List<InventoryBatch> batches = new ArrayList<>();

        int index = 1;

        for (GoodsReceiptDetailRequest detailReq : request.getDetails()) {
            GoodsReceiptDetail detail = GoodsReceiptDetailMapper.toEntity(detailReq);
            detail.setGoodsReceipt(goodsReceipt);

            Product product = productService.findProductById(detailReq.getProductId());
            detail.setProduct(product);

            // ✅ Lấy conversion
            UnitConversion conversion = unitConversionRepository.findById(detailReq.getUnitConversionId())
                    .orElse(null);

            if (conversion == null || conversion.getProduct().getId() != product.getId()) {
                throw new AppException(ErrorCode.UNIT_NOT_FOUND);
            }

            detail.setUnitConversion(conversion); // ⚡ Gán conversion cho detail

            BigDecimal ratio = conversion.getRatioToBase();
            BigDecimal baseQuantity = detailReq.getQuantity().multiply(ratio);
            BigDecimal baseUnitPrice = detailReq.getUnitPrice().divide(ratio, 2, RoundingMode.HALF_UP);

            detail.setTotalPrice(detailReq.getUnitPrice().multiply(detailReq.getQuantity()));
            details.add(detail);

            // ⚡ Batch
            InventoryBatch batch = InventoryBatch.builder()
                    .batchCode(request.getReceiptCode() + "-" + index++)
                    .product(product)
                    .goodsReceiptDetail(detail)
                    .location(locationService.findLocationById(detailReq.getLocationId()))
                    .initialQuantity(baseQuantity)
                    .remainingQuantity(baseQuantity)
                    .unitCost(baseUnitPrice)
                    .createdAt(LocalDateTime.now())
                    .build();

            batches.add(batch);
        }

        goodsReceipt.setDetails(details);

        // 5️⃣ Tính tổng tiền phiếu nhập
        BigDecimal totalAmount = details.stream()
                .map(GoodsReceiptDetail::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        goodsReceipt.setTotalAmount(totalAmount);

        // 6️⃣ Xác định trạng thái thanh toán
//        if (goodsReceipt.getPaidAmount().compareTo(BigDecimal.ZERO) == 0) {
//            goodsReceipt.setPaymentStatus(PaymentStatus.UNPAID);
//        } else if (goodsReceipt.getPaidAmount().compareTo(goodsReceipt.getTotalAmount()) < 0) {
//            goodsReceipt.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
//        } else {
//            goodsReceipt.setPaymentStatus(PaymentStatus.PAID);
//        }

        // 7️⃣ Lưu phiếu nhập và chi tiết
        GoodsReceipt savedReceipt = goodsReceiptRepository.save(goodsReceipt);
        inventoryBatchRepository.saveAll(batches);

        // ghi công nợ
        BigDecimal paidAmount = request.getPaidAmount() != null ? request.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal remainingAmount = totalAmount.subtract(paidAmount);

        DebtStatus debtStatus;
        if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
            debtStatus = DebtStatus.PAID;
        } else if (paidAmount.compareTo(BigDecimal.ZERO) == 0) {
            debtStatus = DebtStatus.UNPAID;
        } else {
            debtStatus = DebtStatus.PARTIALLY_PAID;
        }

        PartnerDebt partnerDebt = PartnerDebt.builder()
                .partner(partner)
                .transactionDate(LocalDateTime.now())
                .description("Nhập hàng - Phiếu " + request.getReceiptCode())
                .totalAmount(totalAmount)
                .paidAmount(paidAmount)
                .remainingAmount(remainingAmount)
                .debtType(DebtType.PAYABLE) // vì là nợ phải trả cho nhà cung cấp
                .status(debtStatus)
                .build();

        partnerDebtRepository.save(partnerDebt);

        return GoodsReceiptMapper.toResponse(savedReceipt);
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
