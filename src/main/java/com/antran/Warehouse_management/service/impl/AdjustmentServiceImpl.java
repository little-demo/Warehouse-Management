package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Adjustment.AdjustmentDetailRequest;
import com.antran.Warehouse_management.dto.request.Adjustment.AdjustmentRequest;
import com.antran.Warehouse_management.dto.response.AdjustmentDetailResponse;
import com.antran.Warehouse_management.dto.response.AdjustmentResponse;
import com.antran.Warehouse_management.entity.InventoryAdjustment;
import com.antran.Warehouse_management.entity.InventoryAdjustmentDetail;
import com.antran.Warehouse_management.entity.InventoryBatch;
import com.antran.Warehouse_management.entity.User;
import com.antran.Warehouse_management.enums.AdjustmentType;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.AdjustmentMapper;
import com.antran.Warehouse_management.repository.InventoryAdjustmentDetailRepository;
import com.antran.Warehouse_management.repository.InventoryAdjustmentRepository;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.service.AdjustmentService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdjustmentServiceImpl implements AdjustmentService {
    InventoryAdjustmentRepository inventoryAdjustmentRepository;
    InventoryAdjustmentDetailRepository inventoryAdjustmentDetailRepository;
    InventoryBatchRepository batchRepo;

    UserServiceImpl userService;

    @Transactional
    @Override
    public AdjustmentResponse createAdjustment(AdjustmentRequest request) {
        User user = userService.findUserById(request.getCreatedBy());
        InventoryAdjustment adjustment = AdjustmentMapper.toEntity(request, user);

        BigDecimal totalDifference = BigDecimal.ZERO;
        List<InventoryAdjustmentDetail> details = new ArrayList<>();

        for (AdjustmentDetailRequest d : request.getDetails()) {
            InventoryBatch batch = batchRepo.findById(d.getInventoryBatchId())
                    .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

            BigDecimal systemQty = batch.getRemainingQuantity();
            BigDecimal actualQty = d.getActualQuantity();

            if (actualQty == null) {
                throw new AppException(ErrorCode.INVALID_QUANTITY);
            }
            if (actualQty.compareTo(BigDecimal.ZERO) < 0) {
                throw new AppException(ErrorCode.NOT_NEGATIVE_QUANTITY);
            }

            BigDecimal diffQty = actualQty.subtract(systemQty);

            // ✅ Kiểm tra hợp lệ
            AdjustmentType type = null;
            if (diffQty.compareTo(BigDecimal.ZERO) > 0) {
                type = AdjustmentType.INCREASE;
            } else if (diffQty.compareTo(BigDecimal.ZERO) < 0) {
                type = AdjustmentType.DECREASE;
            } else {
                type = AdjustmentType.NO_CHANGE;
//                continue;
            }

            BigDecimal totalDiff = diffQty.multiply(batch.getUnitCost());

            InventoryAdjustmentDetail detail = InventoryAdjustmentDetail.builder()
                    .inventoryAdjustment(adjustment)
                    .inventoryBatch(batch)
                    .systemQuantity(systemQty)
                    .actualQuantity(actualQty)
                    .differenceQuantity(diffQty)
                    .unitCost(batch.getUnitCost())
                    .totalDifference(totalDiff)
                    .type(type)
                    .build();
            details.add(detail);
            log.info("Adjustment Detail: {}", detail);

            totalDifference = totalDifference.add(totalDiff);

            batch.setRemainingQuantity(actualQty);
            batchRepo.save(batch);
        }
        log.info("Details: {}", details);
        if (details.isEmpty()) {
            throw new AppException(ErrorCode.NO_ADJUSTMENT_DETAIL);
        }

        adjustment.setDetails(details);
        adjustment.setTotalDifference(totalDifference);

        inventoryAdjustmentRepository.save(adjustment);

        return AdjustmentMapper.toResponse(adjustment);
    }

    @Override
    public AdjustmentResponse getAdjustmentById(int id) {
        return AdjustmentMapper.toResponse(findAdjustmentById(id));
    }

    @Override
    public List<AdjustmentResponse> getAllAdjustments() {
        return inventoryAdjustmentRepository.findAll().stream()
                .map(AdjustmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void cancelAdjustment(int id) {
        InventoryAdjustment adj = findAdjustmentById(id);

        List<InventoryAdjustmentDetail> details = inventoryAdjustmentDetailRepository.findByInventoryAdjustmentId(id);
        for (InventoryAdjustmentDetail d : details) {
            InventoryBatch batch = d.getInventoryBatch();
            BigDecimal restoredQty = batch.getRemainingQuantity().subtract(d.getDifferenceQuantity());
            batch.setRemainingQuantity(restoredQty);
            batchRepo.save(batch);
        }

        adj.setCancelled(true);
        inventoryAdjustmentRepository.save(adj);
    }

    InventoryAdjustment findAdjustmentById(int id) {
        return inventoryAdjustmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADJUSTMENT_NOT_FOUND));
    }
}
