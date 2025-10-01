package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true, nullable = false)
    String sku;
    String name;

    float minStockLevel;
    @Builder.Default
    boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    // Danh sách đơn vị quy đổi (luôn có 1 dòng ratio=1 = baseUnit)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    Set<ProductUnitConversion> conversions = new HashSet<>();
}