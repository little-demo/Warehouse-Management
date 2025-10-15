package com.antran.Warehouse_management.dto.request.GoodsIssue;

import com.antran.Warehouse_management.enums.IssueType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelGoodsRequest {
    String issueCode;
    LocalDateTime issueDate;
    int createdById;
    IssueType issueType;    // Loại xuất: BÁN_HÀNG / HỦY_HÀNG / KHÁC

    List<CancelGoodsDetailRequest> details; // Danh sách chi tiết xuất
}
