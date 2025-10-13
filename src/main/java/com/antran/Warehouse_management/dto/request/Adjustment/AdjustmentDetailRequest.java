package com.antran.Warehouse_management.dto.request.Adjustment;

import com.antran.Warehouse_management.enums.AdjustmentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdjustmentDetailRequest {
    int inventoryBatchId;       // Lô hàng bị điều chỉnh
    BigDecimal actualQuantity;
}
