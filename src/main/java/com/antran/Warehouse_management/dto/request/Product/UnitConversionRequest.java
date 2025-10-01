package com.antran.Warehouse_management.dto.request.Product;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitConversionRequest {
    int unitId;
    BigDecimal ratioToBase;
}
