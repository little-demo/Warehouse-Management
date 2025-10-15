package com.antran.Warehouse_management.dto.request.GoodsIssue;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsIssueDetailRequest {
    int productId;
    int inventoryBatchId;
    Integer unitConversionId;    // Quy đổi đơn vị (nếu có)

    BigDecimal quantity;
}