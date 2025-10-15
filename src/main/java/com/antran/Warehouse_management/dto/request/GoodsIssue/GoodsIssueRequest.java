package com.antran.Warehouse_management.dto.request.GoodsIssue;

import com.antran.Warehouse_management.enums.IssueType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsIssueRequest {
    String issueCode;
    LocalDateTime issueDate;
    int customerId;
    int createdById;
    IssueType issueType;    // Loại xuất: BÁN_HÀNG / HỦY_HÀNG / KHÁC

    BigDecimal amountPaid; // Số tiền khách đã thanh toán (nếu có)
    List<GoodsIssueDetailRequest> details; // Danh sách chi tiết xuất
}
