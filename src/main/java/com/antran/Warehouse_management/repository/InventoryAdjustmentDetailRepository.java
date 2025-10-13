package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.InventoryAdjustmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryAdjustmentDetailRepository extends JpaRepository<InventoryAdjustmentDetail, Integer> {
    List<InventoryAdjustmentDetail> findByInventoryAdjustmentId(int id);
}