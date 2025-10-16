package com.antran.Warehouse_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsIssueDetailResponse {
    String batchCode;
    String productName;
    String displayUnit;
    BigDecimal quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
    BigDecimal conversionRatio;
    BigDecimal baseQuantity;
    BigDecimal baseUnitPrice;
    String locationName;
}
