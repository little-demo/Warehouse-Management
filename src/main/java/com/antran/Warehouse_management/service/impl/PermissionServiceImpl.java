package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.RolePermission.PermissionRequest;
import com.antran.Warehouse_management.dto.response.PermissionResponse;
import com.antran.Warehouse_management.entity.Permission;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.PermissionMapper;
import com.antran.Warehouse_management.repository.PermissionRepository;
import com.antran.Warehouse_management.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = PermissionMapper.toEntity(request);

        return PermissionMapper.toResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionMapper::toResponse)
                .toList();
    }

    @Override
    public void deletePermission(int permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}
