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
    int productId;    // Lô hàng được xuất (liên kết InventoryBatch)
    int unitConversionId;    // Quy đổi đơn vị (nếu có)

    BigDecimal quantity;     // Số lượng xuất theo đơn vị người dùng chọn
    BigDecimal unitPrice;    // Giá bán tính tự động hoặc nhập thủ công
}