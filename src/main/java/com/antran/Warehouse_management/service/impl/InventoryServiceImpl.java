package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.response.InventoryResponse;
import com.antran.Warehouse_management.mapper.InventoryMapper;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    InventoryBatchRepository inventoryBatchRepository;

    @Override
    public List<InventoryResponse> getAllInventory() {
        return inventoryBatchRepository.findAll().stream()
                .map(InventoryMapper::toResponse)
                .toList();
    }
}
