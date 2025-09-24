package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Category.CategoryRequest;
import com.antran.Warehouse_management.dto.response.CategoryResponse;
import com.antran.Warehouse_management.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping()
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .message("Tạo danh mục mới thành công!")
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategories())
                .message("Lấy danh sách danh mục thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CategoryResponse> getCategoryById(@PathVariable int id) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(id))
                .message("Lấy thông tin danh mục thành công!")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable int id, @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(id, request))
                .message("Cập nhật thông tin danh mục thành công!")
                .build();
    }

    @PutMapping("/toggle/{id}")
    ApiResponse<Void> disableCategory(@PathVariable int id) {
        categoryService.disableCategory(id);
        return ApiResponse.<Void>builder()
                .message("Thay đổi trạng thái danh mục thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<Void>builder()
                .message("Xóa danh mục thành công!")
                .build();
    }
}