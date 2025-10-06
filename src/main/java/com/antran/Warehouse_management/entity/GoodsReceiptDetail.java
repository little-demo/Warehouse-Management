package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods_receipt_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsReceiptDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "goods_receipt_id")
    GoodsReceipt goodsReceipt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

//    @ManyToOne
//    @JoinColumn(name = "location_id")
//    Location location;

    BigDecimal quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;

    @OneToMany(mappedBy = "goodsReceiptDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    List<InventoryBatch> inventoryBatches;
}