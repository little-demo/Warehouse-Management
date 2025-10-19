package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.enums.AdjustmentType;
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
public class AdjustmentResponse {
    int id;
    String code;
    LocalDateTime adjustmentDate;
    BigDecimal totalDifference;

    int createdById;
    String createdByName;
    boolean isCancelled;
    List<AdjustmentDetailResponse> details;
}
