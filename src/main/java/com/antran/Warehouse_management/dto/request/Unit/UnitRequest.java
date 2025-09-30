package com.antran.Warehouse_management.dto.request.Unit;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitRequest {
    @NotBlank(message = "UNIT_NAME_NOT_BLANK")
    String name;
}
