package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Partner.PartnerRequest;
import com.antran.Warehouse_management.dto.request.Partner.UpdatePartnerRequest;
import com.antran.Warehouse_management.dto.response.PartnerResponse;
import com.antran.Warehouse_management.service.PartnerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PartnerController {
    PartnerService partnerService;

    @PostMapping()
    ApiResponse<PartnerResponse> createPartner(@RequestBody @Valid PartnerRequest request) {
        return ApiResponse.<PartnerResponse>builder()
                .result(partnerService.createPartner(request))
                .message("Tạo đối tác thành công!")
                .build();
    }

    @GetMapping("{id}")
    ApiResponse<PartnerResponse> getPartnerById(@PathVariable int id) {
        return ApiResponse.<PartnerResponse>builder()
                .result(partnerService.getPartnerById(id))
                .message("Lấy thông tin đối tác thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<PartnerResponse>> getAllPartners() {
        return ApiResponse.<List<PartnerResponse>>builder()
                .result(partnerService.getAllPartners())
                .message("Lấy danh sách đối tác thành công!")
                .build();
    }

    @PutMapping("{id}")
    ApiResponse<PartnerResponse> updatePartner(@PathVariable int id, @RequestBody @Valid UpdatePartnerRequest request) {
        return ApiResponse.<PartnerResponse>builder()
                .result(partnerService.updatePartner(id, request))
                .message("Cập nhật thông tin đối tác thành công!")
                .build();
    }

    @DeleteMapping("{id}")
    ApiResponse<Void> deletePartner(@PathVariable int id) {
        partnerService.deletePartner(id);
        return ApiResponse.<Void>builder()
                .message("Xóa đối tác thành công!")
                .build();
    }
}
