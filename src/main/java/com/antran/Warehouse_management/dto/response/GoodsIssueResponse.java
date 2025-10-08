package com.antran.Warehouse_management.dto.response;

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
public class GoodsIssueResponse {
    int id;
    String issueCode;
    Date issueDate;
    Integer customerId;
    String customerName;
    String issueType;
    Integer createdById;
    String createdByName;
    BigDecimal totalAmount;
    List<GoodsIssueDetailResponse> details;
}
