package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.response.InventoryResponse;
import com.antran.Warehouse_management.entity.InventoryBatch;

public class InventoryMapper {
    public static InventoryResponse toResponse(InventoryBatch inventoryBatch) {
        return InventoryResponse.builder()
                .id(inventoryBatch.getId())
                .batchCode(inventoryBatch.getBatchCode())
                .productName(inventoryBatch.getProduct().getName())
                .locationName(inventoryBatch.getLocation().getName())
                .initialQuantity(inventoryBatch.getInitialQuantity())
                .remainingQuantity(inventoryBatch.getRemainingQuantity())
                .unitCost(inventoryBatch.getUnitCost())
                .build();
    }
}
