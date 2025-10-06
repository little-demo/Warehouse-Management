package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptRequest;
import com.antran.Warehouse_management.dto.response.GoodsReceiptResponse;

import java.util.List;

public interface GoodsReceiptService {
    GoodsReceiptResponse createGoodsReceipt(GoodsReceiptRequest request);
    GoodsReceiptResponse getGoodsReceiptById(int id);
    List<GoodsReceiptResponse> getAllGoodsReceipts();
    GoodsReceiptResponse updateGoodsReceipt(int id, GoodsReceiptRequest request);
    void deleteGoodsReceipt(int id);
}