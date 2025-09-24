package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Location.LocationRequest;
import com.antran.Warehouse_management.dto.response.LocationResponse;
import com.antran.Warehouse_management.entity.Location;
import com.antran.Warehouse_management.entity.Warehouse;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.LocationMapper;
import com.antran.Warehouse_management.repository.LocationRepository;
import com.antran.Warehouse_management.repository.WarehouseRepository;
import com.antran.Warehouse_management.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LocationServiceImpl implements LocationService {
    LocationRepository locationRepository;
    WarehouseRepository warehouseRepository;

    @Override
    public LocationResponse createLocation(LocationRequest request) {
        if (locationRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.LOCATION_NAME_ALREADY_EXISTS);
        }

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));

        Location location = LocationMapper.toEntity(request, warehouse);
        return LocationMapper.toResponse(locationRepository.save(location));
    }

    @Override
    public LocationResponse getLocationById(int id) {
        return LocationMapper.toResponse(findLocationById(id));
    }

    @Override
    public LocationResponse updateLocation(int id, LocationRequest request) {
        if (locationRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.LOCATION_NAME_ALREADY_EXISTS);
        }

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));

        Location location = findLocationById(id);
        location.setName(request.getName());
        location.setType(request.getType());
        location.setWarehouse(warehouse);

        return LocationMapper.toResponse(locationRepository.save(location));
    }

    @Override
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(LocationMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteLocation(int id) {
        Location location = findLocationById(id);
        locationRepository.delete(location);
    }

    Location findLocationById(int id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LOCATION_NOT_EXISTED));
    }
}
