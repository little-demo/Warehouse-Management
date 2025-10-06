package com.antran.Warehouse_management.dto.request.GoodsReceipt;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsReceiptDetailRequest {
    int productId;
    int locationId;

    BigDecimal quantity;
    BigDecimal unitPrice;
}
