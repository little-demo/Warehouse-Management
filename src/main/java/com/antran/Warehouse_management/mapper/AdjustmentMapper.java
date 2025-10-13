package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Adjustment.AdjustmentRequest;
import com.antran.Warehouse_management.dto.response.AdjustmentDetailResponse;
import com.antran.Warehouse_management.dto.response.AdjustmentResponse;
import com.antran.Warehouse_management.entity.InventoryAdjustment;
import com.antran.Warehouse_management.entity.InventoryAdjustmentDetail;
import com.antran.Warehouse_management.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class AdjustmentMapper {
    public static InventoryAdjustment toEntity(AdjustmentRequest request, User user) {
        return InventoryAdjustment.builder()
                .adjustmentDate(LocalDateTime.now())
                .createdBy(user)
                .isCancelled(false)
                .build();
    }

    public static AdjustmentResponse toResponse(InventoryAdjustment entity) {
        return AdjustmentResponse.builder()
                .id(entity.getId())
                .adjustmentDate(entity.getAdjustmentDate())
                .totalDifference(entity.getTotalDifference())
                .createdById(entity.getCreatedBy().getId())
                .createdByName(entity.getCreatedBy().getUsername())
                .isCancelled(entity.isCancelled())
                .details(entity.getDetails() != null ?
                        entity.getDetails().stream()
                                .map(AdjustmentMapper::toDetailResponse)
                                .toList() : List.of())
                .build();
    }

    public static AdjustmentDetailResponse toDetailResponse(InventoryAdjustmentDetail detail) {
        return AdjustmentDetailResponse.builder()
                .id(detail.getId())
                .inventoryBatchId(detail.getInventoryBatch().getId())
                .batchCode(detail.getInventoryBatch().getBatchCode())
                .type(detail.getType())
                .systemQuantity(detail.getSystemQuantity())
                .actualQuantity(detail.getActualQuantity())
                .differenceQuantity(detail.getDifferenceQuantity())
                .unitCost(detail.getUnitCost())
                .totalDifference(detail.getTotalDifference())
                .build();
    }
}
