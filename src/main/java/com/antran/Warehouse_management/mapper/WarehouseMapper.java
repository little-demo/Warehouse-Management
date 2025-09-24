package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Warehouse.WarehouseRequest;
import com.antran.Warehouse_management.dto.response.WarehouseResponse;
import com.antran.Warehouse_management.entity.Warehouse;

public class WarehouseMapper {
    public static WarehouseResponse toResponse(Warehouse warehouse) {
        if (warehouse == null) return null;
        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .address(warehouse.getAddress())
                .isActive(warehouse.isActive())
                .build();
    }

    public static Warehouse toEntity(WarehouseRequest request) {
        if (request == null) return null;
        return Warehouse.builder()
                .name(request.getName())
                .address(request.getAddress())
                .isActive(true)
                .build();
    }
}
