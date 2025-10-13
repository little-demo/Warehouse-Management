package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.GoodsIssue.GoodsIssueRequest;
import com.antran.Warehouse_management.dto.request.GoodsIssue.CancelGoodsRequest;
import com.antran.Warehouse_management.dto.response.GoodsIssueResponse;
import com.antran.Warehouse_management.entity.Customer;
import com.antran.Warehouse_management.entity.GoodsIssue;
import com.antran.Warehouse_management.entity.User;

import java.util.ArrayList;
import java.util.Date;

public class GoodsIssueMapper {
    public static GoodsIssue toEntity(GoodsIssueRequest request, User user, Customer customer) {
        return GoodsIssue.builder()
                .issueCode(request.getIssueCode())
                .issueDate(Date.from(new Date().toInstant()))
                .issueType(request.getIssueType())
                .createdBy(user)
                .customer(customer)
                .details(new ArrayList<>())
                .build();
    }

    public static GoodsIssueResponse toResponse(GoodsIssue goodsIssue) {
        return GoodsIssueResponse.builder()
                .id(goodsIssue.getId())
                .issueCode(goodsIssue.getIssueCode())
                .issueDate(goodsIssue.getIssueDate())
                .issueType(goodsIssue.getIssueType().name())
                .totalAmount(goodsIssue.getTotalAmount())
                .createdById(goodsIssue.getCreatedBy().getId())
                .createdByName(goodsIssue.getCreatedBy().getFullName())
                .customerId(goodsIssue.getCustomer() != null ? goodsIssue.getCustomer().getId() : null)
                .customerName(goodsIssue.getCustomer() != null ? goodsIssue.getCustomer().getName() : null)
                .details(goodsIssue.getDetails().stream()
                        .map(GoodsIssueDetailMapper::toResponse)
                        .toList())
                .build();
    }

    public static GoodsIssue toEntity(CancelGoodsRequest request, User user) {
        return GoodsIssue.builder()
                .issueCode(request.getIssueCode())
                .issueDate(Date.from(new Date().toInstant()))
                .issueType(request.getIssueType())
                .createdBy(user)
                .details(new ArrayList<>())
                .build();
    }
}
