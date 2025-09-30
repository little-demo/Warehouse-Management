package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Unit.UnitRequest;
import com.antran.Warehouse_management.dto.response.UnitResponse;

import java.util.List;

public interface UnitService {
    UnitResponse createUnit(UnitRequest request);
    UnitResponse getUnitById(int id);
    List<UnitResponse> getAllUnits();
    void deleteUnit(int id);
}
