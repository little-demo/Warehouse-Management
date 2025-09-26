package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.RolePermission.RoleRequest;
import com.antran.Warehouse_management.dto.response.RoleResponse;
import com.antran.Warehouse_management.entity.Role;

import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(role.getPermissions().stream()
                        .map(PermissionMapper::toResponse)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Role toEntity(RoleRequest request) {
        return Role.builder()
                .name(request.getName())
                .build();
    }
}
