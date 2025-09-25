package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.enums.Gender;
import com.antran.Warehouse_management.enums.ERole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    String username;
    String fullName;
    Gender gender;
    String email;
    String phone;
    LocalDate dob;
    boolean enabled;
    ERole role;
    Set<Integer> warehouseIds;
}
