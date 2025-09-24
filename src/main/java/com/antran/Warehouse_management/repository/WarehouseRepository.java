package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}