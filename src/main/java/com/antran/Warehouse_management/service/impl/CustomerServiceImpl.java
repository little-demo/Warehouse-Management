package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Customer.CustomerRequest;
import com.antran.Warehouse_management.dto.response.CustomerResponse;
import com.antran.Warehouse_management.entity.Customer;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.CustomerMapper;
import com.antran.Warehouse_management.repository.CustomerRepository;
import com.antran.Warehouse_management.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.toEntity(request);

        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse getCustomerById(int id) {
        return CustomerMapper.toResponse(findCustomerById(id));
    }

    @Override
    public CustomerResponse updateCustomer(int id, CustomerRequest request) {
        Customer customer = findCustomerById(id);
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());

        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCustomer(int id) {
        Customer customer = findCustomerById(id);
        customerRepository.delete(customer);
    }

    Customer findCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }
}
