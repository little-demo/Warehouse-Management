package com.antran.Warehouse_management.service;


import com.antran.Warehouse_management.dto.request.Partner.PartnerRequest;
import com.antran.Warehouse_management.dto.request.Partner.UpdatePartnerRequest;
import com.antran.Warehouse_management.dto.response.PartnerResponse;

import java.util.List;

public interface PartnerService {
    PartnerResponse createPartner(PartnerRequest request);
    PartnerResponse updatePartner(int id, UpdatePartnerRequest request);
    PartnerResponse getPartnerById(int id);
    List<PartnerResponse> getAllPartners();
    void deletePartner(int id);
}
