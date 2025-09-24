package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.response.ProductResponse;
import com.antran.Warehouse_management.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping()
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .message("Tạo sản phẩm thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .message("Lấy danh sách sản phẩm thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable int id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .message("Lấy thông tin sản phẩm thành công!")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable int id, @RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, request))
                .message("Cập nhật thông tin sản phẩm thành công!")
                .build();
    }

    @PutMapping("/toggle/{id}")
    ApiResponse<Void> disableProduct(@PathVariable int id) {
        productService.changeProductStatus(id);
        return ApiResponse.<Void>builder()
                .message("Thay đổi trạng thái sản phẩm thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .message("Xóa sản phẩm thành công!")
                .build();
    }
}