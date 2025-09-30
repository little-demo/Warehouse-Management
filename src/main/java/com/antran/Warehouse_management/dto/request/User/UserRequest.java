package com.antran.Warehouse_management.dto.request.User;

import com.antran.Warehouse_management.enums.Gender;
import com.antran.Warehouse_management.enums.ERole;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @NotBlank(message = "USERNAME_NOT_BLANK")
    @Size(min = 6, message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "PASSWORD_NOT_BLANK")
    @Size(min = 6,message = "PASSWORD_INVALID")
    String password;

    @NotBlank(message = "FULLNAME_NOT_BLANK")
    String fullName;

    @NotNull(message = "GENDER_NOT_BLANK")
    Gender gender;

    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;

    @Pattern(regexp = "0\\d{9}", message = "PHONE_INVALID")
    String phone;

    @Past(message = "DOB_INVALID")
    LocalDate dob;

    @NotEmpty(message = "ROLES_NOT_BLANK")
    Set<ERole> roles;

    Set<Integer> warehouseIds;
}
