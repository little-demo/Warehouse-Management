package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Location.LocationRequest;
import com.antran.Warehouse_management.dto.response.LocationResponse;
import com.antran.Warehouse_management.entity.Location;
import com.antran.Warehouse_management.entity.Warehouse;

public class LocationMapper {
    public static Location toEntity(LocationRequest request, Warehouse warehouse) {
        if (request == null) return null;
        return Location.builder()
                .name(request.getName())
                .type(request.getType())
                .warehouse(warehouse)
                .build();
    }

    public static LocationResponse toResponse(Location location) {
        if (location == null) return null;
        return LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .type(location.getType())
                .warehouseName(location.getWarehouse().getName())
                .build();
    }
}
