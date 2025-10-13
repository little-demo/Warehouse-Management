package com.antran.Warehouse_management.dto.request.Location;

import com.antran.Warehouse_management.enums.LocationType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationRequest {
    @NotBlank(message = "LOCATION_NAME_NOT_BE_EMPTY")
    String name;
//    int warehouseId;
    LocationType type;
}
