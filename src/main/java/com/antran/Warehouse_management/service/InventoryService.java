package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventory();
}
