package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.response.ProductResponse;
import com.antran.Warehouse_management.entity.Category;
import com.antran.Warehouse_management.entity.Product;

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
                .build();
    }

    public static Product toEntity(ProductRequest request, Category category) {
        if (request == null) return null;

        return Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .minStockLevel(request.getMinStockLevel())
                .baseUnit(request.getBaseUnit())
                .category(category)
                .build();
    }
}
