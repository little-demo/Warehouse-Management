package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueRequest;
import com.antran.Warehouse_management.dto.request.GoodsIssue.CancelGoodsRequest;
import com.antran.Warehouse_management.dto.response.GoodsIssueResponse;

import java.util.List;

public interface GoodsIssueService {
    GoodsIssueResponse createGoodsIssue(GoodsIssueRequest request);
    GoodsIssueResponse getGoodsIssueById(int id);
    List<GoodsIssueResponse> getAllGoodsIssues();
    void deleteGoodsIssue(int id);
    GoodsIssueResponse cancelGoodsIssue(CancelGoodsRequest request);
}
