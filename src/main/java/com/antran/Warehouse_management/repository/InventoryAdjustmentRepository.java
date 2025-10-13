package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.InventoryAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAdjustmentRepository extends JpaRepository<InventoryAdjustment, Integer> {
}