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
    EMAIL_INVALID(2014, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(2015, "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0", HttpStatus.BAD_REQUEST),
    SUPPLIER_NAME_NOT_BLANK(2016, "Tên nhà cung cấp không được để trống", HttpStatus.BAD_REQUEST),
    SUPPLIER_PHONE_NOT_BLANK(2017, "Số điện thoại nhà cung cấp không được để trống", HttpStatus.BAD_REQUEST),
    SUPPLIER_EXISTED(2018, "Tên nhà cung cấp đã tồn tại!", HttpStatus.BAD_REQUEST),
    SUPPLIER_NOT_FOUND(2019, "Nhà cung cấp không tồn tại!", HttpStatus.NOT_FOUND),
    USERNAME_NOT_BLANK(2020, "Username không được để trống", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_BLANK(2021, "Password không được để trống", HttpStatus.BAD_REQUEST),
    FULLNAME_NOT_BLANK(2022, "Họ và tên không được để trống", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_BLANK(2023, "Email không được để trống", HttpStatus.BAD_REQUEST),
    DOB_INVALID(2024, "Ngày sinh phải là ngày trong quá khứ", HttpStatus.BAD_REQUEST),
    GENDER_NOT_BLANK(2025, "Giới tính không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_NOT_BLANK(2026, "Role không được để trống", HttpStatus.BAD_REQUEST),
    WAREHOUSE_REQUIRED(2027, "Phải chọn ít nhất một kho cho nhân viên kho", HttpStatus.BAD_REQUEST),
    WAREHOUSE_NOT_FOUND(2028, "Kho không tồn tại!", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_WAREHOUSE_ACCESS(2029, "Bạn không có quyền truy cập kho này", HttpStatus.FORBIDDEN),
    ROLE_NAME_NOT_BE_EMPTY(3001, "Tên vai trò không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(3002, "Tên vai trò đã tồn tại!", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(3003, "Vai trò không tồn tại!", HttpStatus.NOT_FOUND),
    PERMISSION_NAME_NOT_BE_EMPTY(3004, "Tên quyền không được để trống", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(3005, "Tên quyền đã tồn tại!", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(3006, "Quyền không tồn tại!", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(3007, "Vai trò không tồn tại!", HttpStatus.NOT_FOUND),
    CUSTOMER_NAME_NOT_BLANK(3008, "Tên khách hàng không được để trống", HttpStatus.BAD_REQUEST),
    CUSTOMER_PHONE_NOT_BLANK(3009, "Số điện thoại khách hàng không được để trống", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND(3010, "Khách hàng không tồn tại!", HttpStatus.NOT_FOUND),
    UNIT_NAME_NOT_BLANK(3011, "Tên đơn vị không được để trống", HttpStatus.BAD_REQUEST),
    UNIT_NOT_FOUND(3012, "Đơn vị không tồn tại!", HttpStatus.NOT_FOUND),
    INVALID_CONVERSION_RATIO(3013, "Tỉ lệ quy đổi phải là số dương", HttpStatus.BAD_REQUEST),
    UNIT_ALREADY_EXISTS_AS_BASE(3014, "Đơn vị quy đổi đã tồn tại dưới dạng đơn vị cơ bản", HttpStatus.BAD_REQUEST),
    RECEIPT_CODE_ALREADY_EXISTS(3015, "Mã phiếu nhập đã tồn tại!", HttpStatus.BAD_REQUEST),
    GOODS_RECEIPT_NOT_FOUND(3016, "Phiếu nhập kho không tồn tại!", HttpStatus.NOT_FOUND),
    GOODS_RECEIPT_CANNOT_BE_DELETED(3017, "Không thể xóa phiếu nhập vì đã phát sinh giao dịch.", HttpStatus.BAD_REQUEST),
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
