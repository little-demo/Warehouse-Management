package com.antran.Warehouse_management.dto.request.GoodsReceipt;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsReceiptRequest {
    String receiptCode;
    LocalDateTime receiptDate;
    int partnerId;
    int createdById;
    BigDecimal paidAmount;
    List<GoodsReceiptDetailRequest> details;
}