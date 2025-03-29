package com.wccg.well_c_code_git_backend.infrastructure.oauth.controller;

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

    @GetMapping("/login/github")
    public ResponseEntity<GithubLoginUrlResponse> getGithubLoginUrl() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(githubOAuthService.generateLoginUrl());
    }

    @GetMapping("/callback/github")
    public ResponseEntity<String> githubCallback(@RequestParam("code") String code) {
        githubOAuthService.processGithubCallback(code);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Github 로그인에 성공 했습니다.");
    }
}
