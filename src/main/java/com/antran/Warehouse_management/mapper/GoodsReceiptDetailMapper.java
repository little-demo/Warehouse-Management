package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptDetailRequest;
import com.antran.Warehouse_management.dto.response.GoodsReceiptDetailResponse;
import com.antran.Warehouse_management.entity.GoodsReceiptDetail;

import java.math.BigDecimal;

public class GoodsReceiptDetailMapper {
    public static GoodsReceiptDetail toEntity(GoodsReceiptDetailRequest request) {
        BigDecimal totalPrice = request.getUnitPrice().multiply(request.getQuantity());
        return GoodsReceiptDetail.builder()
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .totalPrice(totalPrice)
                .build();
    }

    public static GoodsReceiptDetailResponse toResponse(GoodsReceiptDetail detail) {
        return GoodsReceiptDetailResponse.builder()
                .id(detail.getId())
                .productName(detail.getProduct().getName())
//                .locationName(detail.getLocation().getName())
                .quantity(detail.getQuantity())
                .unitPrice(detail.getUnitPrice())
                .totalPrice(detail.getTotalPrice())
                .build();
    }
}
