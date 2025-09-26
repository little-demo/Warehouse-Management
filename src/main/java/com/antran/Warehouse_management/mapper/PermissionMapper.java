package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.RolePermission.PermissionRequest;
import com.antran.Warehouse_management.dto.response.PermissionResponse;
import com.antran.Warehouse_management.entity.Permission;

public class PermissionMapper {
    public static PermissionResponse toResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }

    public static Permission toEntity(PermissionRequest request) {
        return Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
