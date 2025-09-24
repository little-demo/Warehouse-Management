package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Location.LocationRequest;
import com.antran.Warehouse_management.dto.response.LocationResponse;

import java.util.List;

public interface LocationService {
    LocationResponse createLocation(LocationRequest request);
    LocationResponse getLocationById(int id);
    LocationResponse updateLocation(int id, LocationRequest request);
    List<LocationResponse> getAllLocations();
    void deleteLocation(int id);
}
