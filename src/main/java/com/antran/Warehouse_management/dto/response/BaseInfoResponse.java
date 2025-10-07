package com.antran.Warehouse_management.dto.response;

import com.antran.Warehouse_management.entity.User;
import lombok.Data;

@Data
public class BaseInfoResponse {
    private int id;
    private String name;

    public BaseInfoResponse(User user){
        this.id = user.getId();
        this.name = user.getFullName();
    }
}
