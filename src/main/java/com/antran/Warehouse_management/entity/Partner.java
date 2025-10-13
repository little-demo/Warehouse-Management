package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.PartnerType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "partners")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String address;
    String phone;
    String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "partner_type")
    PartnerType partnerType;
}
