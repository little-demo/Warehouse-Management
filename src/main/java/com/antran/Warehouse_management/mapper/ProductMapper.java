package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.response.CategoryResponse;
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
                .unit(product.getUnit())
                .minStockLevel(product.getMinStockLevel())
                .isActive(product.isActive())
                .category(CategoryResponse.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .isActive(product.getCategory().isActive())
                        .build()
                )
                .build();
    }

    public static Product toEntity(ProductRequest request, Category category) {
        if (request == null) return null;
        return Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .unit(request.getUnit())
                .minStockLevel(request.getMinStockLevel())
                .category(category)
                .build();
    }
}
