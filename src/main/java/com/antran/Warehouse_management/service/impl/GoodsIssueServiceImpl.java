package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueRequest;
import com.antran.Warehouse_management.dto.response.GoodsIssueResponse;
import com.antran.Warehouse_management.repository.GoodsIssueRepository;
import com.antran.Warehouse_management.repository.InventoryBatchRepository;
import com.antran.Warehouse_management.service.GoodsIssueService;
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
public class GoodsIssueServiceImpl implements GoodsIssueService {
    GoodsIssueRepository goodsIssueRepository;
    InventoryBatchRepository inventoryBatchRepository;

    @Override
    public GoodsIssueResponse createGoodsIssue(GoodsIssueRequest request) {
        return null;
    }

    @Override
    public GoodsIssueResponse getGoodsIssueById(int id) {
        return null;
    }

    @Override
    public List<GoodsIssueResponse> getAllGoodsIssues() {
        return List.of();
    }

    @Override
    public void deleteGoodsIssue(int id) {

    }
}
