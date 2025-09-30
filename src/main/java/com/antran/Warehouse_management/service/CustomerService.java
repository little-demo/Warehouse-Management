package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Customer.CustomerRequest;
import com.antran.Warehouse_management.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse getCustomerById(int id);
    CustomerResponse updateCustomer(int id, CustomerRequest request);
    List<CustomerResponse> getAllCustomers();
    void deleteCustomer(int id);
}