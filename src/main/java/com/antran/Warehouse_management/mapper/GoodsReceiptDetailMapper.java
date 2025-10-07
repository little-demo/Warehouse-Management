package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptDetailRequest;
import com.antran.Warehouse_management.dto.response.GoodsReceiptDetailResponse;
import com.antran.Warehouse_management.entity.GoodsReceiptDetail;
import com.antran.Warehouse_management.entity.Product;
import com.antran.Warehouse_management.entity.UnitConversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        Product product = detail.getProduct();
        UnitConversion conversion = detail.getUnitConversion();

        String baseUnit = product.getBaseUnit();
        String displayUnit = (conversion != null) ? conversion.getUnitName() : baseUnit;
        BigDecimal ratio = (conversion != null) ? conversion.getRatioToBase() : BigDecimal.ONE;

        // Quy đổi về đơn vị gốc
        BigDecimal baseQuantity = detail.getQuantity().multiply(ratio);
        BigDecimal baseUnitPrice = detail.getUnitPrice().divide(ratio, 2, RoundingMode.HALF_UP);

        return GoodsReceiptDetailResponse.builder()
                .id(detail.getId())
                .productName(product.getName())
                .quantity(detail.getQuantity())
                .unitPrice(detail.getUnitPrice())
                .totalPrice(detail.getTotalPrice())

                .displayUnit(displayUnit)
                .conversionRatio(ratio)
                .baseUnit(baseUnit)
                .baseQuantity(baseQuantity)
                .baseUnitPrice(baseUnitPrice)
                .build();
    }
}
