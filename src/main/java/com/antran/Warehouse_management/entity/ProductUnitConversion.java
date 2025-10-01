package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(
        name = "product_unit_conversions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "unit_id"}) //đảm bảo 1 product - 1 unit duy nhất
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUnitConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    Unit unit;

    @Column(nullable = false)
    BigDecimal ratioToBase; // BaseUnit là ratio = 1
    // VD: 1 Thùng = 24 Chai → ratioToBase = 24
}