package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByName(String name);
}