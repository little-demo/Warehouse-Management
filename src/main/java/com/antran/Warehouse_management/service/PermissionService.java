package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.RolePermission.PermissionRequest;
import com.antran.Warehouse_management.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void deletePermission(int permissionId);
}
