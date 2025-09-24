package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Category.CategoryRequest;
import com.antran.Warehouse_management.dto.response.CategoryResponse;
import com.antran.Warehouse_management.entity.Category;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.CategoryMapper;
import com.antran.Warehouse_management.repository.CategoryRepository;
import com.antran.Warehouse_management.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = CategoryMapper.toEntity(request);
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(int id, CategoryRequest request) {
        Category category = findCategoryById(id);
        category.setName(request.getName());
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(int id) {
        return CategoryMapper.toResponse(findCategoryById(id));
    }

    @Override
    public void deleteCategory(int id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public void disableCategory(int id) {
        Category category = findCategoryById(id);
        category.setActive(!category.isActive());
        categoryRepository.save(category);
    }

    Category findCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    return new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
                });
    }
}
