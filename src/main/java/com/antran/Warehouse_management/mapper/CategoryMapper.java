package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Category.CategoryRequest;
import com.antran.Warehouse_management.dto.response.CategoryResponse;
import com.antran.Warehouse_management.entity.Category;

public class CategoryMapper {
    public static CategoryResponse toResponse(Category category) {
        if (category == null) return null;
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .isActive(category.isActive())
                .build();
    }

    public static Category toEntity(CategoryRequest request) {
        if (request == null) return null;
        return Category.builder()
                .name(request.getName())
                .build();
    }
}