package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Warehouse.WarehouseRequest;
import com.antran.Warehouse_management.dto.response.WarehouseResponse;
import com.antran.Warehouse_management.entity.Warehouse;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.WarehouseMapper;
import com.antran.Warehouse_management.repository.WarehouseRepository;
import com.antran.Warehouse_management.service.WarehouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    WarehouseRepository warehouseRepository;

    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest request) {
        Warehouse warehouse = WarehouseMapper.toEntity(request);

        return WarehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    @Override
    //@PreAuthorize("@warehouseSecurity.checkWarehouse(authentication, #id)")
    public WarehouseResponse getWarehouseById(int id) {
        return WarehouseMapper.toResponse(findWarehouseById(id));
    }

    @Override
    public WarehouseResponse updateWarehouse(int id, WarehouseRequest request) {
        Warehouse warehouse = findWarehouseById(id);
        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        return WarehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    @Override
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(WarehouseMapper::toResponse)
                .toList();
    }

    @Override
    public void disableWarehouse(int id) {
        Warehouse warehouse = findWarehouseById(id);
        warehouse.setActive(!warehouse.isActive());
        warehouseRepository.save(warehouse);
    }

    @Override
    public void deleteWarehouse(int id) {
        Warehouse warehouse = findWarehouseById(id);
        warehouseRepository.delete(warehouse);
    }

    Warehouse findWarehouseById(int id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
    }
}
