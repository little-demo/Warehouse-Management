package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsBySku(String sku);
}