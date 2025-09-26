package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.RolePermission.PermissionRequest;
import com.antran.Warehouse_management.dto.response.PermissionResponse;
import com.antran.Warehouse_management.service.PermissionService;
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
public class PermissionController {
    PermissionService permissionService;

    @PostMapping()
    ApiResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .message("Tạo quyền thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .message("Lấy danh sách quyền thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deletePermission(@PathVariable int id) {
        permissionService.deletePermission(id);
        return ApiResponse.<Void>builder()
                .message("Xoá quyền thành công!")
                .build();
    }
}
