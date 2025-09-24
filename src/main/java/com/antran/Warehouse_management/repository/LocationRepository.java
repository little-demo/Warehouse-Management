package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    boolean existsByName(String name);
}