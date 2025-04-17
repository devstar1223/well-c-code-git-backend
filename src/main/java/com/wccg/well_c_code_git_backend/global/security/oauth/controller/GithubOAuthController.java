package com.wccg.well_c_code_git_backend.global.security.oauth.controller;

import com.wccg.well_c_code_git_backend.global.security.oauth.dto.GithubLoginUrlResponse;
import com.wccg.well_c_code_git_backend.global.security.oauth.dto.LoginResponse;
import com.wccg.well_c_code_git_backend.global.security.oauth.service.GithubOAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class GithubOAuthController {

    private final GithubOAuthService githubOAuthService;

    @Operation(
            summary = "GitHub 로그인 URL 발급",
            description = """
                    클라이언트가 GitHub 로그인을 시작할 수 있도록 GitHub OAuth 인증 페이지 URL을 반환합니다.
                    
                    해당 URL로 리다이렉트하면 GitHub 로그인 화면으로 이동합니다.
                    """
    )
    @GetMapping("/login/github")
    public ResponseEntity<GithubLoginUrlResponse> getGithubLoginUrl() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(githubOAuthService.generateLoginUrl());
    }

    @Operation(
            summary = "GitHub OAuth 콜백 처리",
            description = """
                    GitHub OAuth 로그인 후 리디렉션된 URI에서 인가 코드를 받아
                    서버에서 GitHub 액세스 토큰을 요청하고 사용자 정보를 조회한 뒤
                    사용자 정보와 액세스 토큰을 DB에 저장합니다.
                    
                    이후 이용자의 JWT를 발급하여 간단한 이용자 정보와 함께 응답합니다.
                    
                    이 엔드포인트는 GitHub의 redirect_uri로 등록되어 있어야 하며,
                    클라이언트가 아닌 브라우저가 직접 접근하게 됩니다.
                    따라서 차후 프론트엔드 콜백 방식으로 대체할 예정입니다.
                    """
    )
    @GetMapping("/callback/github")
    public ResponseEntity<LoginResponse> githubCallback(@RequestParam("code") String code) {
        LoginResponse loginResponse = githubOAuthService.processGithubCallback(code);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginResponse);
    }
}
