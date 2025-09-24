package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.LocationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "locations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    Warehouse warehouse;

    @Enumerated(EnumType.STRING) // lưu enum dưới dạng text thay vì số
    @Column(nullable = false)
    LocationType type;
}
