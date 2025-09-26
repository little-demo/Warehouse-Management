package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.RolePermission.RoleRequest;
import com.antran.Warehouse_management.dto.response.RoleResponse;
import com.antran.Warehouse_management.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping()
    ApiResponse<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .message("Tạo vai trò thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .message("Lấy danh sách vai trò thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder()
                .message("Xoá vai trò thành công!")
                .build();
    }
}
