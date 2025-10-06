package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.GoodsReceipt.GoodsReceiptRequest;
import com.antran.Warehouse_management.dto.response.GoodsReceiptResponse;
import com.antran.Warehouse_management.service.GoodsReceiptService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-receipts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoodsReceiptController {
    GoodsReceiptService goodsReceiptService;

    @PostMapping()
    ApiResponse<GoodsReceiptResponse> createGoodsReceipt(@RequestBody @Valid GoodsReceiptRequest request) {
        return ApiResponse.<GoodsReceiptResponse>builder()
                .result(goodsReceiptService.createGoodsReceipt(request))
                .message("Tạo phiếu nhập kho thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<GoodsReceiptResponse> getGoodsReceiptById(@PathVariable int id) {
        return ApiResponse.<GoodsReceiptResponse>builder()
                .result(goodsReceiptService.getGoodsReceiptById(id))
                .message("Lấy phiếu nhập kho thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<GoodsReceiptResponse>> getAllGoodsReceipts() {
        return ApiResponse.<List<GoodsReceiptResponse>>builder()
                .result(goodsReceiptService.getAllGoodsReceipts())
                .message("Lấy danh sách phiếu nhập kho thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteGoodsReceipt(@PathVariable int id) {
        goodsReceiptService.deleteGoodsReceipt(id);
        return ApiResponse.<Void>builder()
                .message("Xóa phiếu nhập kho thành công!")
                .build();
    }
}
