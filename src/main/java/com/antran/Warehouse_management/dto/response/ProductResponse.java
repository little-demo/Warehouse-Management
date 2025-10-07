package com.antran.Warehouse_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
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

    String baseUnit;
    float minStockLevel;
    boolean isActive;

    int categoryId;
    String categoryName;

    List<UnitConversionResponse> conversions;
}