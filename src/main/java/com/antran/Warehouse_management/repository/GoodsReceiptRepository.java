package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.GoodsReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt, Integer> {
    boolean existsByReceiptCode(String receiptCode);
}