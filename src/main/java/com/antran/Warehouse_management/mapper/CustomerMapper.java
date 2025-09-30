package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Customer.CustomerRequest;
import com.antran.Warehouse_management.dto.response.CustomerResponse;
import com.antran.Warehouse_management.entity.Customer;

public class CustomerMapper {
    public static Customer toEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return Customer.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
    }

    public static CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }
}
