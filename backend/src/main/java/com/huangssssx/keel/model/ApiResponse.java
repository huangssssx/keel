package com.huangssssx.keel.model;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Integer code;
    private Integer status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(200);
        response.setStatus(200);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setStatus(code);
        response.setMessage(message);
        return response;
    }
}