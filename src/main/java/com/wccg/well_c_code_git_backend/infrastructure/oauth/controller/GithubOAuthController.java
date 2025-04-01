package com.wccg.well_c_code_git_backend.infrastructure.oauth.controller;

import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.infrastructure.jwt.JwtProvider;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.LoginResponse;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.service.GithubOAuthService;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.GithubLoginUrlResponse;
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
    private final JwtProvider jwtProvider;

    @GetMapping("/login/github")
    public ResponseEntity<GithubLoginUrlResponse> getGithubLoginUrl() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(githubOAuthService.generateLoginUrl());
    }

    @GetMapping("/callback/github")
    public ResponseEntity<LoginResponse> githubCallback(@RequestParam("code") String code) {
        LoginResponse loginResponse = githubOAuthService.processGithubCallback(code);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginResponse);
    }
}
