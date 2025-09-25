package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    boolean existsByName(String name);
}