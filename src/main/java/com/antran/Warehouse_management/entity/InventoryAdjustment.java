package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.AdjustmentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inventory_adjustments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryAdjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true, nullable = false)
    String code;
    LocalDateTime adjustmentDate;
    BigDecimal totalDifference; //tính tự động khi thêm mới hoặc cập nhật

    @ManyToOne
    @JoinColumn(name = "user_id")
    User createdBy;
    @Builder.Default
    boolean isCancelled = false;

    @OneToMany(mappedBy = "inventoryAdjustment", cascade = CascadeType.ALL, orphanRemoval = true)
    List<InventoryAdjustmentDetail> details;
}
