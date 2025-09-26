package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.RolePermission.RoleRequest;
import com.antran.Warehouse_management.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRoles();
    void deleteRole(int roleId);
}