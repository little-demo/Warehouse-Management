package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}