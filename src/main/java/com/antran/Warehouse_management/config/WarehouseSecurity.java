package com.antran.Warehouse_management.config;

import com.antran.Warehouse_management.dto.request.User.CustomUserPrincipal;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("warehouseSecurity")
public class WarehouseSecurity {

//    public boolean checkWarehouse(Authentication authentication, Integer warehouseId) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new AppException(ErrorCode.UNAUTHORIZED);
//        }
//
//        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
//
//        // chỉ check khi role là WAREHOUSE_STAFF
//        boolean isStaff = principal.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_WAREHOUSE_STAFF"));
//
//        if (isStaff) {
//            if (warehouseId == null) {
////                throw new AppException(ErrorCode.WAREHOUSE_REQUIRED);
//            }
//
////            if (!principal.getWarehouseIds().contains(warehouseId)) {
////                throw new AppException(ErrorCode.UNAUTHORIZED_WAREHOUSE_ACCESS);
////            }
//        }
//
//        // nếu không phải staff → mặc định cho qua
//        return true;
//    }
}
