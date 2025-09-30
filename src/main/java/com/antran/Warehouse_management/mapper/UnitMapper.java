package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Unit.UnitRequest;
import com.antran.Warehouse_management.dto.response.UnitResponse;
import com.antran.Warehouse_management.entity.Unit;

public class UnitMapper {
    public static Unit toEntity(UnitRequest request) {
        return Unit.builder()
                .name(request.getName())
                .build();
    }

    public static UnitResponse toResponse(Unit unit) {
        return UnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .build();
    }
}
