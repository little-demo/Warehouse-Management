package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}