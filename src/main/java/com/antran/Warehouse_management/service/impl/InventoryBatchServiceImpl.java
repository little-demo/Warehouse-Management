package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.response.InventoryResponse;
import com.antran.Warehouse_management.entity.InventoryBatch;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.InventoryMapper;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.service.InventoryBatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InventoryBatchServiceImpl implements InventoryBatchService {
    InventoryBatchRepository inventoryBatchRepository;

    @Override
    public InventoryResponse getInventoryBatchById(int id) {
        return new InventoryResponse(inventoryBatchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND)));
    }

    @Override
    public List<InventoryResponse> getAllInventoryBatches() {
        return inventoryBatchRepository.findAll().stream().map(InventoryResponse::new).toList();
    }

    @Override
    public List<InventoryResponse> getInventoryBatchesByProductId(int productId) {
        return inventoryBatchRepository
                .findByProduct_IdAndRemainingQuantityGreaterThanOrderByCreatedAtDesc(
                        productId, BigDecimal.ZERO
                )
                .stream()
                .map(InventoryResponse::new)
                .toList();
    }

    @Override
    public InventoryResponse getInventoryBatchByBatchCode(String batchCode) {
        InventoryBatch batch = inventoryBatchRepository
                .findByBatchCode(batchCode)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        log.info("Found inventory batch: {}", batch);
        return InventoryMapper.toResponse(batch);
    }
}
