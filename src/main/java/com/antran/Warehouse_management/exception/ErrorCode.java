package com.antran.Warehouse_management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username phải có ít nhất {min} kí tự", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password phải có ít nhất {min} kí tự", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    INCORRECT_PASSWORD(1008, "Mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    USER_DISABLED(1010, "Người dùng đã bị vô hiệu hóa", HttpStatus.FORBIDDEN),
    OLD_PASSWORD_INCORRECT(1011, "Mật khẩu cũ không chính xác", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_NOT_MATCH(1012, "Mật khẩu xác nhận không khớp", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_NEW_PASSWORD_MATCH(1013, "Mật khẩu mới không được trùng với mật khẩu cũ", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_CAN_NOT_BE_EMPTY(1014, "Mật khẩu cũ không được để trống", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_CAN_NOT_BE_EMPTY(1015, "Mật khẩu mới không được để trống", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY(1016, "Mật khẩu xác nhận không được để trống", HttpStatus.BAD_REQUEST),
    NAME_WAREHOUSE_NOT_BE_EMPTY(2001, "Tên kho không được để trống", HttpStatus.BAD_REQUEST),
    WAREHOUSE_NOT_EXISTED(2002, "Kho không tồn tại!", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_NOT_BE_EMPTY(2003, "Tên danh mục không được để trống", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(2004, "Danh mục không tồn tại!", HttpStatus.NOT_FOUND),
    PRODUCT_SKU_ALREADY_EXISTS(2005, "Mã SKU đã tồn tại!", HttpStatus.BAD_REQUEST),
    SKU_NOT_BE_EMPTY(2006, "SKU không được để trống", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_NOT_BE_EMPTY(2007, "Tên sản phẩm không được để trống", HttpStatus.BAD_REQUEST),
    UNIT_NOT_BE_EMPTY(2008, "Đơn vị tính không được để trống", HttpStatus.BAD_REQUEST),
    MIN_STOCK_LEVEL_MUST_BE_POSITIVE(2009, "Mức tồn kho tối thiểu phải là số dương", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(2010, "Sản phẩm không tồn tại!", HttpStatus.NOT_FOUND),
    LOCATION_NAME_NOT_BE_EMPTY(2011, "Tên vị trí không được để trống", HttpStatus.BAD_REQUEST),
    LOCATION_NAME_ALREADY_EXISTS(2012, "Tên vị trí đã tồn tại!", HttpStatus.BAD_REQUEST),
    LOCATION_NOT_EXISTED(2013, "Vị trí không tồn tại!", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
