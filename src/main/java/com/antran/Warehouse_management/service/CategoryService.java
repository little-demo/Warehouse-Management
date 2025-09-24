package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Category.CategoryRequest;
import com.antran.Warehouse_management.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(int id, CategoryRequest request);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(int id);
    void deleteCategory(int id);
    void disableCategory(int id);
}
