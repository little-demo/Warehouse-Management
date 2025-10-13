package com.antran.Warehouse_management.dto.request.Partner;

import com.antran.Warehouse_management.enums.PartnerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerRequest {
    @NotBlank(message = "NAME_NOT_BLANK")
    String name;
    String address;
    @NotBlank(message = "PHONE_NOT_BLANK")
    @Pattern(regexp = "0\\d{9}", message = "PHONE_INVALID")
    String phone;
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotNull(message = "PARTNER_TYPE_NOT_NULL")
    PartnerType partnerType;
}
