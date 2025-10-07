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

    String displayUnit; // Đơn vị người nhập chọn
    BigDecimal conversionRatio; // Tỷ lệ quy đổi -> base
    String baseUnit; // Đơn vị gốc
    BigDecimal baseQuantity; // Số lượng gốc
    BigDecimal baseUnitPrice; // Giá / đơn vị gốc
}