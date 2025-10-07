package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.enums.PaymentStatus;
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
public class GoodsReceiptResponse {
    int id;
    String receiptCode;
    LocalDateTime receiptDate;
    int supplierId;
    String supplierName;
    int createdById;
    String createdByName;
//    BaseInfoResponse createdBy;
    BigDecimal totalAmount;
    BigDecimal paidAmount;
    PaymentStatus paymentStatus;
    List<GoodsReceiptDetailResponse> details;
}
