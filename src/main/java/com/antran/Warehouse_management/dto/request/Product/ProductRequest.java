package com.antran.Warehouse_management.dto.request.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "SKU_NOT_BE_EMPTY")
    String sku;
    @NotBlank(message = "PRODUCT_NAME_NOT_BE_EMPTY")
    String name;
    @NotBlank(message = "UNIT_NOT_BE_EMPTY")
    String unit;
    @PositiveOrZero(message = "MIN_STOCK_LEVEL_MUST_BE_POSITIVE")
    float minStockLevel;
    int categoryId;
}