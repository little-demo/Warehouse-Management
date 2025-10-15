package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueRequest;
import com.antran.Warehouse_management.dto.request.GoodsIssue.CancelGoodsRequest;
import com.antran.Warehouse_management.dto.response.GoodsIssueResponse;
import com.antran.Warehouse_management.service.GoodsIssueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-issues")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoodsIssueController {
    GoodsIssueService goodsIssueService;

    @PostMapping()
    ApiResponse<GoodsIssueResponse> createGoodsIssue(@RequestBody GoodsIssueRequest request) {
        return ApiResponse.<GoodsIssueResponse>builder()
                .result(goodsIssueService.createGoodsIssue(request))
                .message("Tạo phiếu xuất kho thành công!")
                .build();
    }

    @PostMapping("/cancel")
    ApiResponse<GoodsIssueResponse> cancelGoods(@RequestBody CancelGoodsRequest request) {
        return ApiResponse.<GoodsIssueResponse>builder()
                .result(goodsIssueService.cancelGoodsIssue(request))
                .message("Tạo phiếu hủy hàng thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<GoodsIssueResponse>> getAllGoodsIssues() {
        return ApiResponse.<List<GoodsIssueResponse>>builder()
                .result(goodsIssueService.getAllGoodsIssues())
                .message("Lấy danh sách phiếu xuất kho thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<GoodsIssueResponse> getGoodsIssueById(@PathVariable int id) {
        return ApiResponse.<GoodsIssueResponse>builder()
                .result(goodsIssueService.getGoodsIssueById(id))
                .message("Lấy phiếu xuất kho thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteGoodsIssue(@PathVariable int id) {
        goodsIssueService.deleteGoodsIssue(id);
        return ApiResponse.<Void>builder()
                .message("Xóa phiếu xuất kho thành công!")
                .build();
    }
}