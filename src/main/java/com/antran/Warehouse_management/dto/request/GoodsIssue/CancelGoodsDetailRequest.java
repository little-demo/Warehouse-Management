package com.antran.Warehouse_management.dto.request.GoodsIssue;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelGoodsDetailRequest {
    int inventoryBatchId;     // Lô hàng được hủy
    int unitConversionId;
    BigDecimal quantity;
    BigDecimal unitPrice;   //tính tổn thất
}
