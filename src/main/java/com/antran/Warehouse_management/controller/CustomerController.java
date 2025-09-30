package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Customer.CustomerRequest;
import com.antran.Warehouse_management.dto.request.Supplier.SupplierRequest;
import com.antran.Warehouse_management.dto.response.CustomerResponse;
import com.antran.Warehouse_management.dto.response.SupplierResponse;
import com.antran.Warehouse_management.service.CustomerService;
import com.antran.Warehouse_management.service.SupplierService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CustomerService customerService;

    @PostMapping()
    ApiResponse<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.createCustomer(request))
                .message("Tạo khách hàng mới thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<CustomerResponse>> getAllCustomers() {
        return ApiResponse.<List<CustomerResponse>>builder()
                .result(customerService.getAllCustomers())
                .message("Lấy danh sách khách hàng thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CustomerResponse> getSupplierById(@PathVariable int id) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.getCustomerById(id))
                .message("Lấy thông tin khách hàng thành công!")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CustomerResponse> updateCustomer(@PathVariable int id, @RequestBody @Valid CustomerRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.updateCustomer(id, request))
                .message("Cập nhật thông tin khách hàng thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ApiResponse.<Void>builder()
                .message("Xoá khách hàng thành công!")
                .build();
    }
}