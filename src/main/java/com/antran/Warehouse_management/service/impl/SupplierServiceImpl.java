package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Supplier.SupplierRequest;
import com.antran.Warehouse_management.dto.response.SupplierResponse;
import com.antran.Warehouse_management.entity.Supplier;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.SupplierMapper;
import com.antran.Warehouse_management.repository.SupplierRepository;
import com.antran.Warehouse_management.service.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {
    SupplierRepository supplierRepository;

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {
        if (supplierRepository.existsByName(request.getName())) {
             throw new AppException(ErrorCode.SUPPLIER_EXISTED);
        }
        Supplier supplier = SupplierMapper.toEntity(request);

        return SupplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse updateSupplier(int id, SupplierRequest request) {
        Supplier supplier = findSupplierById(id);

        if (!supplier.getName().equals(request.getName()) && supplierRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.SUPPLIER_EXISTED);
        }

        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());

        return SupplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse getSupplierById(int id) {
        return SupplierMapper.toResponse(findSupplierById(id));
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteSupplier(int id) {
        Supplier supplier = findSupplierById(id);
        supplierRepository.delete(supplier);
    }

    @Override
    public void changeSupplierStatus(int id) {
        Supplier supplier = findSupplierById(id);
        supplier.setActive(!supplier.isActive());
        supplierRepository.save(supplier);
    }

    private Supplier findSupplierById(int id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
    }
}
