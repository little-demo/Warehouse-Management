package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Unit.UnitRequest;
import com.antran.Warehouse_management.dto.response.UnitResponse;
import com.antran.Warehouse_management.entity.Unit;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.UnitMapper;
import com.antran.Warehouse_management.repository.UnitRepository;
import com.antran.Warehouse_management.service.UnitService;
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
public class UnitServiceImpl implements UnitService {
    UnitRepository unitRepository;

    @Override
    public UnitResponse createUnit(UnitRequest request) {
        Unit unit = UnitMapper.toEntity(request);

        return UnitMapper.toResponse(unitRepository.save(unit));
    }

    @Override
    public UnitResponse getUnitById(int id) {
        Unit unit = findUnitById(id);
        return UnitMapper.toResponse(unit);
    }

    @Override
    public List<UnitResponse> getAllUnits() {
        return unitRepository.findAll().stream()
                .map(UnitMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteUnit(int id) {
        Unit unit = findUnitById(id);
        unitRepository.delete(unit);
    }

    Unit findUnitById(int id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
    }
}
