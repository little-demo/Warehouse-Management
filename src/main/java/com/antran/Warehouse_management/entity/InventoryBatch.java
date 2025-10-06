package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "inventory_batches")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String batchCode;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "goods_receipt_detail_id")
    GoodsReceiptDetail goodsReceiptDetail;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    BigDecimal initialQuantity;
    BigDecimal remainingQuantity;
    BigDecimal unitCost;
}
