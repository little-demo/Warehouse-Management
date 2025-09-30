package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.entity.ProductUnitConversion;
import com.antran.Warehouse_management.entity.Unit;
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
    UnitResponse unit;
    float minStockLevel;
    boolean isActive;
    //UnitResponse baseUnit;
    int baseUnitId;
    String baseUnitName;
    //CategoryResponse category;
    int categoryId;
    String categoryName;
    Set<ProductUnitConversionResponse> conversions;
}