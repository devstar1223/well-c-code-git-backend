package com.wccg.well_c_code_git_backend.global.dto;

import com.wccg.well_c_code_git_backend.global.exception.dto.ErrorResponse;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
public class ApiResponse<T> {
    HttpStatus httpStatus;
    private final boolean success;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK,true, message, data);
    }

    public static ApiResponse<ErrorResponse> failure(ErrorResponse errorResponse) {
        return new ApiResponse<>(HttpStatus.valueOf(errorResponse.getStatus()), false, errorResponse.getMessage(), errorResponse);
    }
}