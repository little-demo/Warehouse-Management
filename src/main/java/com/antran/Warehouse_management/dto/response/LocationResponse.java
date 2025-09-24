package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.enums.LocationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationResponse {
    int id;
    String name;
    String warehouseName;
    LocationType type;
}