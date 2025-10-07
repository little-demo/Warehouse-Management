package com.antran.Warehouse_management.dto.response;

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
    int customerId;
    String customerName;
    String createdBy;
    BigDecimal totalAmount;
    List<GoodsIssueDetailResponse> goodsIssueDetails;
}
