package com.wccg.well_c_code_git_backend.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wccg.well_c_code_git_backend.global.dto.ApiResponse; // ApiResponse import 추가
import com.wccg.well_c_code_git_backend.global.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.FORBIDDEN,
                "AUTH-002",
                "접근 권한이 없습니다.",
                request
        );

        ApiResponse<ErrorResponse> apiResponse = ApiResponse.failure(errorResponse);

        response.setStatus(apiResponse.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String body = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(body);
    }
}