package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.AdjustmentType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "inventory_adjustment_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAdjustmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    AdjustmentType type;

    @ManyToOne
    @JoinColumn(name = "inventory_adjustment_id")
    InventoryAdjustment inventoryAdjustment;

    @ManyToOne
    @JoinColumn(name = "inventory_batch_id")
    InventoryBatch inventoryBatch;

    BigDecimal systemQuantity;
    BigDecimal actualQuantity;
    BigDecimal differenceQuantity; // actual - system
    BigDecimal unitCost;
    BigDecimal totalDifference; // differenceQuantity * unitCost
}
