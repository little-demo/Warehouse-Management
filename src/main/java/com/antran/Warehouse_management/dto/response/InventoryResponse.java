package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.entity.InventoryBatch;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryResponse {
    int id;
    String batchCode;
    String productName;
    String locationName;
    BigDecimal initialQuantity;
    BigDecimal remainingQuantity;
    BigDecimal unitCost;

    public InventoryResponse(InventoryBatch inventoryBatch) {
        this.id = inventoryBatch.getId();
        this.batchCode = inventoryBatch.getBatchCode();
        this.productName = inventoryBatch.getProduct().getName();
        this.locationName = inventoryBatch.getLocation().getName();
        this.initialQuantity = inventoryBatch.getInitialQuantity();
        this.remainingQuantity = inventoryBatch.getRemainingQuantity();
        this.unitCost = inventoryBatch.getUnitCost();
    }
}
