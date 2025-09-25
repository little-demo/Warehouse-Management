package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Supplier.SupplierRequest;
import com.antran.Warehouse_management.dto.response.SupplierResponse;
import com.antran.Warehouse_management.service.SupplierService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierController {
    SupplierService supplierService;

    @PostMapping()
    ApiResponse<SupplierResponse> createSupplier(@RequestBody @Valid SupplierRequest request) {
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.createSupplier(request))
                .message("Tạo nhà cung cấp mới thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<SupplierResponse>> getAllSuppliers() {
        return ApiResponse.<List<SupplierResponse>>builder()
                .result(supplierService.getAllSuppliers())
                .message("Lấy danh sách nhà cung cấp thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<SupplierResponse> getSupplierById(@PathVariable int id) {
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.getSupplierById(id))
                .message("Lấy thông tin nhà cung cấp thành công!")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SupplierResponse> updateSupplier(@PathVariable int id, @RequestBody @Valid SupplierRequest request) {
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.updateSupplier(id, request))
                .message("Cập nhật thông tin nhà cung cấp thành công!")
                .build();
    }

    @PutMapping("toggle/{id}")
    ApiResponse<Void> changeSupplierStatus(@PathVariable int id) {
        supplierService.changeSupplierStatus(id);
        return ApiResponse.<Void>builder()
                .message("Thay đổi trạng thái nhà cung cấp thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);
        return ApiResponse.<Void>builder()
                .message("Xoá nhà cung cấp thành công!")
                .build();
    }
}
