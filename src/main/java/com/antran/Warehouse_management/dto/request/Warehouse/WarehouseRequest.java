package com.antran.Warehouse_management.dto.request.Warehouse;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseRequest {
    @NotBlank(message = "NAME_WAREHOUSE_NOT_BE_EMPTY")
    String name;
    String address;
}
