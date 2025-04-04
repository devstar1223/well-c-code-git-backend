package com.wccg.well_c_code_git_backend.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.user.UserService;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorResponse;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwt = authHeader.substring(7);

                if (!jwtProvider.validateToken(jwt)) {
                    throw new BadCredentialsException("유효하지 않은 토큰입니다.");
                }

                Long userId = jwtProvider.getUserId(jwt);
                User user = userService.getUserById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("해당 id를 가진 이용자가 없습니다."));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()))
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

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

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(responseBody);
    }
}
