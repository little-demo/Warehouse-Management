package com.antran.Warehouse_management.dto.request.User;

import com.antran.Warehouse_management.enums.Gender;
import com.antran.Warehouse_management.enums.ERole;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @NotBlank
    @Size(min = 6, message = "USERNAME_INVALID")
    String username;

    @NotBlank
    @Size(min = 6,message = "PASSWORD_INVALID")
    String password;

    @NotBlank
    String fullName;

    @NotNull(message = "Giới tính không được để trống")
    Gender gender;

    @NotBlank
    @Email
    String email;

    @Pattern(regexp = "0\\d{9}", message = "Số điện thoại phải có đúng 10 chữ số và bắt đầu bằng 0")
    String phone;

    @Past
    LocalDate dob;

    @NotNull(message = "Role không được để trống")
    ERole role;
}
