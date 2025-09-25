package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Supplier.SupplierRequest;
import com.antran.Warehouse_management.dto.response.SupplierResponse;

import java.util.List;

public interface SupplierService {
    SupplierResponse createSupplier(SupplierRequest request);
    SupplierResponse updateSupplier(int id, SupplierRequest request);
    SupplierResponse getSupplierById(int id);
    List<SupplierResponse> getAllSuppliers();
    void deleteSupplier(int id);
    void changeSupplierStatus(int id);
}
