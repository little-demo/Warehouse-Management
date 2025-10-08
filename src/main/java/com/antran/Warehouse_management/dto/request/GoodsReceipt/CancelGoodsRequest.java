package com.antran.Warehouse_management.dto.request.GoodsReceipt;

import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueDetailRequest;
import com.antran.Warehouse_management.enums.IssueType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelGoodsRequest {
    String issueCode;

    int createdById;
    IssueType issueType;    // Loại xuất: BÁN_HÀNG / HỦY_HÀNG / KHÁC

    List<CancelGoodsDetailRequest> details; // Danh sách chi tiết xuất
}
