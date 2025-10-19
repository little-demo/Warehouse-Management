package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryBatchService {
    InventoryResponse getInventoryBatchById(int id);
    List<InventoryResponse> getAllInventoryBatches();
    List<InventoryResponse> getInventoryBatchesByProductId(int productId);
    InventoryResponse getInventoryBatchByBatchCode(String batchCode);
}