package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "unit_conversions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    String unitName; // Tên đơn vị quy đổi, VD: Thùng, Chai, Hộp, Gói...

    @Column(nullable = false)
    BigDecimal ratioToBase; // BaseUnit là ratio = 1
    // VD: 1 Thùng = 24 Chai → ratioToBase = 24
}