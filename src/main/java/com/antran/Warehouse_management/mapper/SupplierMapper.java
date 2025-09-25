package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Supplier.SupplierRequest;
import com.antran.Warehouse_management.dto.response.SupplierResponse;
import com.antran.Warehouse_management.entity.Supplier;

public class SupplierMapper {
    public static SupplierResponse toResponse(Supplier supplier) {
        if (supplier == null) return null;
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .isActive(supplier.isActive())
                .build();
    }

    public static Supplier toEntity(SupplierRequest request) {
        if (request == null) return null;

        return Supplier.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
    }
}