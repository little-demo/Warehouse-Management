package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.response.CategoryResponse;
import com.antran.Warehouse_management.dto.response.ProductResponse;
import com.antran.Warehouse_management.dto.response.ProductUnitConversionResponse;
import com.antran.Warehouse_management.entity.Category;
import com.antran.Warehouse_management.entity.Product;
import com.antran.Warehouse_management.entity.Unit;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductResponse toResponse(Product product) {
        if (product == null) return null;
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .minStockLevel(product.getMinStockLevel())
                .isActive(product.isActive())

                // category
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : 0)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)

                // conversions (bao gồm baseUnit ratio=1)
                .conversions(product.getConversions() == null ? Set.of() :
                        product.getConversions().stream()
                                .map(conv -> ProductUnitConversionResponse.builder()
                                        .id(conv.getId())
                                        .unitId(conv.getUnit().getId())
                                        .unitName(conv.getUnit().getName())
                                        .ratioToBase(conv.getRatioToBase())
                                        .build()
                                )
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public static Product toEntity(ProductRequest request, Category category) {
        if (request == null) return null;

        return Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .minStockLevel(request.getMinStockLevel())
                .category(category)
                .build();
    }
}
