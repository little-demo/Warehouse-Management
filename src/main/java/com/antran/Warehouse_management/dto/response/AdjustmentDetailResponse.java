package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.enums.AdjustmentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdjustmentDetailResponse {
    int id;
    int inventoryBatchId;
    String batchCode; // nếu InventoryBatch có mã riêng
    AdjustmentType type;
    BigDecimal systemQuantity;
    BigDecimal actualQuantity;
    BigDecimal differenceQuantity;
    BigDecimal unitCost;
    BigDecimal totalDifference;
}