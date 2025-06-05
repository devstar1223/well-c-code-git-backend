package com.wccg.well_c_code_git_backend.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.global.dto.ApiResponse; // ApiResponse import 추가
import com.wccg.well_c_code_git_backend.global.exception.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;
    private static final List<String> STATIC_EXCLUDE_PREFIXES = List.of(
            "/api/h2-console/",
            "/api/swagger-ui",
            "/api/v3/api-docs",
            "/api/oauth"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        try {
            if (isExcludePath(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = resolveToken(request);
            validateToken(token);
            User user = getAuthenticatedUser(token);
            setAuthentication(user);

            filterChain.doFilter(request, response);

        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            handleAuthException(response, request, ex, HttpStatus.UNAUTHORIZED, "AUTH-001");
        } catch (Exception ex) {
            handleAuthException(response, request, ex, HttpStatus.INTERNAL_SERVER_ERROR, "AUTH-999");
        }
    }

    private void handleAuthException(HttpServletResponse response, HttpServletRequest request,
                                     Exception ex, HttpStatus status, String code) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(
                status,
                code,
                ex.getMessage(),
                request
        );

        ApiResponse<ErrorResponse> apiResponse = ApiResponse.failure(errorResponse);

        response.setStatus(apiResponse.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String responseBody = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(responseBody);
    }

    private boolean isExcludePath(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if (STATIC_EXCLUDE_PREFIXES.stream().anyMatch(requestURI::startsWith)) {
            return true;
        }

        if ("GET".equalsIgnoreCase(method)) {
            return jwtProperties.getExcludePaths().stream()
                    .anyMatch(requestURI::equals);
        }

        return false;
    }

    private String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Authorization 헤더가 없거나 올바르지 않습니다.");
        }
        return authHeader.substring(7);
    }

    private void validateToken(String token) {
        if (!jwtProvider.validateToken(token)) {
            throw new BadCredentialsException("유효하지 않은 토큰입니다.");
        }
    }

    private User getAuthenticatedUser(String token) {
        Long userId = jwtProvider.getUserId(token);
        return userService.getUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 id를 가진 이용자가 없습니다."));
    }

    private void setAuthentication(User user) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}