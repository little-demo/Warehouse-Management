package com.antran.Warehouse_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    int id;
    String sku;
    String name;

    float minStockLevel;
    boolean isActive;

    int categoryId;
    String categoryName;

    // Bao gồm cả baseUnit (ratio = 1) và các conversions khác
    Set<ProductUnitConversionResponse> conversions;
}