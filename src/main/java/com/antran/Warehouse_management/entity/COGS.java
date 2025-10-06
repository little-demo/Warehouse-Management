package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cogs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class COGS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "goods_issue_detail_id")
    GoodsReceiptDetail goodsReceiptDetail;

    @ManyToOne
    @JoinColumn(name = "inventory_batch_id")
    InventoryBatch inventoryBatch;

    BigDecimal quantity;
    BigDecimal unitCost;
    BigDecimal totalAmount;
}