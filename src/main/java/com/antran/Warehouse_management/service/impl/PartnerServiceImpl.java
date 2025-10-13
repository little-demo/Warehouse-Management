package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Partner.PartnerRequest;
import com.antran.Warehouse_management.dto.request.Partner.UpdatePartnerRequest;
import com.antran.Warehouse_management.dto.response.PartnerResponse;
import com.antran.Warehouse_management.entity.Partner;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.PartnerMapper;
import com.antran.Warehouse_management.repository.PartnerRepository;
import com.antran.Warehouse_management.service.PartnerService;
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
public class PartnerServiceImpl implements PartnerService {
    PartnerRepository partnerRepository;

    @Override
    public PartnerResponse createPartner(PartnerRequest request) {
        Partner partner = PartnerMapper.toEntity(request);
        return PartnerMapper.toResponse(partnerRepository.save(partner));
    }

    @Override
    public PartnerResponse updatePartner(int id, UpdatePartnerRequest request) {
        Partner partner = findPartnerById(id);
        partner.setName(request.getName());
        partner.setAddress(request.getAddress());
        partner.setPhone(request.getPhone());
        partner.setEmail(request.getEmail());
        return PartnerMapper.toResponse(partnerRepository.save(partner));
    }

    @Override
    public PartnerResponse getPartnerById(int id) {
        return PartnerMapper.toResponse(findPartnerById(id));
    }

    @Override
    public List<PartnerResponse> getAllPartners() {
        return partnerRepository.findAll().stream()
                .map(PartnerMapper::toResponse)
                .toList();
    }

    @Override
    public void deletePartner(int id) {
        Partner partner = findPartnerById(id);
        partnerRepository.delete(partner);
    }

    Partner findPartnerById(int id){
        return partnerRepository.findById(id).orElseThrow(()->{
            return new AppException(ErrorCode.PARTNER_NOT_FOUND);
        });
    }
}
