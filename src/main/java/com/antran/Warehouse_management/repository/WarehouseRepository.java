package com.antran.Warehouse_management.repository;

import com.antran.Warehouse_management.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Set<Warehouse> findAllByIdIn(Collection<Integer> ids);
}