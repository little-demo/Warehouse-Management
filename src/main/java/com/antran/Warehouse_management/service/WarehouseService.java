package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Warehouse.WarehouseRequest;
import com.antran.Warehouse_management.dto.response.WarehouseResponse;

import java.util.List;

public interface WarehouseService {
    WarehouseResponse createWarehouse(WarehouseRequest request);
    WarehouseResponse getWarehouseById(int id);
    WarehouseResponse updateWarehouse(int id, WarehouseRequest request);
    List<WarehouseResponse> getAllWarehouses();
    void disableWarehouse(int id);
    void deleteWarehouse(int id);
}
