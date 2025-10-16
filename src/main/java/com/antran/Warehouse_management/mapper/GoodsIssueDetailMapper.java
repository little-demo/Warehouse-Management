package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.response.GoodsIssueDetailResponse;
import com.antran.Warehouse_management.entity.GoodsIssueDetail;
import com.antran.Warehouse_management.entity.InventoryBatch;
import com.antran.Warehouse_management.entity.Product;
import com.antran.Warehouse_management.entity.UnitConversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GoodsIssueDetailMapper {
    public static GoodsIssueDetailResponse toResponse(GoodsIssueDetail detail) {
        UnitConversion conv = detail.getUnitConversion();
        InventoryBatch batch = detail.getInventoryBatch();
        Product product = batch.getProduct();

        BigDecimal ratio = conv != null ? conv.getRatioToBase() : BigDecimal.ONE;
        BigDecimal baseQty = detail.getQuantity().multiply(ratio);
        BigDecimal basePrice = detail.getUnitPrice().divide(ratio, 2, RoundingMode.HALF_UP);

        return GoodsIssueDetailResponse.builder()
                .batchCode(batch.getBatchCode())
                .productName(product.getName())
                .displayUnit(conv != null ? conv.getUnitName() : product.getBaseUnit())
                .conversionRatio(ratio)
                .quantity(detail.getQuantity())
                .unitPrice(detail.getUnitPrice())
                .totalPrice(detail.getTotalPrice())
                .baseQuantity(baseQty)
                .baseUnitPrice(basePrice)
                .locationName(detail.getInventoryBatch().getLocation().getName())
                .build();
    }
}
