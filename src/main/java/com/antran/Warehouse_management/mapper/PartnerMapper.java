package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.Partner.PartnerRequest;
import com.antran.Warehouse_management.dto.response.PartnerResponse;
import com.antran.Warehouse_management.entity.Partner;

public class PartnerMapper {
    public static Partner toEntity(PartnerRequest request){
      return Partner.builder()
              .name(request.getName())
              .email(request.getEmail())
              .phone(request.getPhone())
              .address(request.getAddress())
              .partnerType(request.getPartnerType())
              .build();
    };

    public static PartnerResponse toResponse(Partner partner){
        return PartnerResponse.builder()
                .id(partner.getId())
                .name(partner.getName())
                .email(partner.getEmail())
                .phone(partner.getPhone())
                .address(partner.getAddress())
                .partnerType(partner.getPartnerType())
                .build();
    }
}
