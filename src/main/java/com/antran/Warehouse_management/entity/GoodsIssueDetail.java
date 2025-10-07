package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "goods_issue_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsIssueDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "goods_issue_id")
    GoodsIssue goodsIssue;

    @ManyToOne
    @JoinColumn(name = "inventory_batch_id")
    InventoryBatch inventoryBatch;

    @ManyToOne
    @JoinColumn(name = "unit_conversion_id")
    UnitConversion unitConversion;

    BigDecimal quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
}
