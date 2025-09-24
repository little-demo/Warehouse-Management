package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.Gender;
import com.antran.Warehouse_management.enums.ERole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    String password;
    String fullName;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender")
    Gender gender;
    String email;
    String phone;
    LocalDate dob;
    @Builder.Default
    boolean enabled = true;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role")
    ERole role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_warehouses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_id")
    )
    Set<Warehouse> warehouses = new HashSet<>();
}
