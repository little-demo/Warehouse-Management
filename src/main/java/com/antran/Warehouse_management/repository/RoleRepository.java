package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  boolean existsByName(String name);
}