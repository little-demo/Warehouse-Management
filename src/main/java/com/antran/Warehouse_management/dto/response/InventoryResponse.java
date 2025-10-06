package com.antran.Warehouse_management.dto.response;

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
}
