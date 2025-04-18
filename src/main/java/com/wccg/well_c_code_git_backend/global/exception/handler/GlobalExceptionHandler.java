package com.wccg.well_c_code_git_backend.global.exception.handler;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;
import com.wccg.well_c_code_git_backend.global.exception.dto.ErrorResponse;
import com.wccg.well_c_code_git_backend.global.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBaseException(
            BaseException exception,
            HttpServletRequest request
    ) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(
                errorCode.getStatus(),
                errorCode.getCode(),
                errorCode.getMessage(),
                request
        );

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.failure(errorResponse));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException exception) {
        throw exception;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnexpectedException(Exception exception, HttpServletRequest request) {
        log.error("[Unhandled Exception] URI: {} / Message: {}", request.getRequestURI(), exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "E999",
                "서버 내부에 알수 없는 오류가 발생했습니다.",
                request
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(errorResponse));
    }
}