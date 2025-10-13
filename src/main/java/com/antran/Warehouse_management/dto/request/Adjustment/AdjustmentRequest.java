package com.antran.Warehouse_management.dto.request.Adjustment;

import com.antran.Warehouse_management.enums.AdjustmentType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdjustmentRequest {
    int createdBy; // user id

    List<AdjustmentDetailRequest> details;
}
