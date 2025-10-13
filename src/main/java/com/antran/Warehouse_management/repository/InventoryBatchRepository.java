package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.InventoryBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Integer> {
    @Query("SELECT b FROM InventoryBatch b WHERE b.goodsReceiptDetail.goodsReceipt.id = :receiptId")
    List<InventoryBatch> findByGoodsReceiptId(@Param("receiptId") int receiptId);

    List<InventoryBatch> findByProductIdOrderByCreatedAtAsc(int productId);

    @Query("SELECT COALESCE(AVG(b.unitCost), 0) FROM InventoryBatch b " +
            "WHERE b.product.id = :productId AND b.remainingQuantity > 0")
    BigDecimal calculateAverageCost(@Param("productId") int productId);

    // *** THÊM: Method chỉ lấy batch CÒN HÀNG và sắp xếp FIFO ***
    List<InventoryBatch> findByProductIdAndRemainingQuantityGreaterThanOrderByCreatedAtAsc(
            int productId, BigDecimal remainingQuantity);

    // *** THÊM: Tính tổng tồn kho ***
    @Query("SELECT COALESCE(SUM(b.remainingQuantity), 0) FROM InventoryBatch b WHERE b.product.id = :productId")
    BigDecimal calculateTotalStock(@Param("productId") int productId);
}