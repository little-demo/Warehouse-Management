package com.antran.Warehouse_management.dto.request.Product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    String unitName;

    @DecimalMin(value = "0.0001", message = "INVALID_CONVERSION_RATIO")
    BigDecimal ratioToBase;
}
