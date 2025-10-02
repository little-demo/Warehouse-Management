package com.antran.Warehouse_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitConversionResponse {
    int id;
    int unitId;
    String unitName;
    BigDecimal ratioToBase; // 1 unit = ratioToBase*baseUnit
}
