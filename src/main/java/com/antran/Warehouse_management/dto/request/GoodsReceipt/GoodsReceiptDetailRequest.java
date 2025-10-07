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

    int unitConversionId; // Đơn vị quy đổi khi nhập hàng, VD: Thùng, Chai, Hộp, Gói...
}