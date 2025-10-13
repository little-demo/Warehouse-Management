package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptRequest;
import com.antran.Warehouse_management.dto.response.GoodsReceiptResponse;
import com.antran.Warehouse_management.entity.GoodsReceipt;

import java.time.LocalDateTime;

public class GoodsReceiptMapper {
    public static GoodsReceipt toEntity(GoodsReceiptRequest request) {
        return GoodsReceipt.builder()
                .receiptCode(request.getReceiptCode())
                .receiptDate(request.getReceiptDate())
//                .paidAmount(request.getPaidAmount())
                .build();
    }

    public static GoodsReceiptResponse toResponse(GoodsReceipt goodsReceipt) {
        return GoodsReceiptResponse.builder()
                .id(goodsReceipt.getId())
                .receiptCode(goodsReceipt.getReceiptCode())
                .receiptDate(goodsReceipt.getReceiptDate())
                .partnerId(goodsReceipt.getPartner().getId())
                .partnerName(goodsReceipt.getPartner().getName())
                .createdById(goodsReceipt.getCreatedBy().getId())
                .createdByName(goodsReceipt.getCreatedBy().getFullName())
                .totalAmount(goodsReceipt.getTotalAmount())
//                .paidAmount(goodsReceipt.getPaidAmount())
//                .paymentStatus(goodsReceipt.getPaymentStatus())
                .details(goodsReceipt.getDetails() == null ? null :
                        goodsReceipt.getDetails().stream()
                                .map(GoodsReceiptDetailMapper::toResponse)
                                .toList()
                )
                .build();
    }
}
