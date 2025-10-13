package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Adjustment.AdjustmentRequest;
import com.antran.Warehouse_management.dto.response.AdjustmentResponse;

import java.util.List;

public interface AdjustmentService {
    AdjustmentResponse createAdjustment(AdjustmentRequest request);
    AdjustmentResponse getAdjustmentById(int id);
    List<AdjustmentResponse> getAllAdjustments();
    void cancelAdjustment(int id);
}
