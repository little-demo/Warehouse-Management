package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.InventoryBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Integer> {
    @Query("SELECT b FROM InventoryBatch b WHERE b.goodsReceiptDetail.goodsReceipt.id = :receiptId")
    List<InventoryBatch> findByGoodsReceiptId(@Param("receiptId") int receiptId);
}