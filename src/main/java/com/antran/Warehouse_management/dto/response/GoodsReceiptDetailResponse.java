package com.antran.Warehouse_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsReceiptDetailResponse {
    int id;
    String productName;
//    String locationName;
    BigDecimal quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
}