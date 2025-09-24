package com.antran.Warehouse_management.dto.request.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserChangePasswordRequest {
    @NotBlank(message = "OLD_PASSWORD_CAN_NOT_BE_EMPTY")
    String oldPassword;
    @NotBlank(message = "NEW_PASSWORD_CAN_NOT_BE_EMPTY")
    @Size(min = 6, message = "PASSWORD_INVALID")
    String newPassword;
    @NotBlank(message = "CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY")
    String confirmNewPassword;
}
