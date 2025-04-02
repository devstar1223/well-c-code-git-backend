package com.wccg.well_c_code_git_backend.global.security.oauth;

import com.wccg.well_c_code_git_backend.global.security.oauth.dto.GithubAccessTokenResponse;
import com.wccg.well_c_code_git_backend.global.security.oauth.dto.GithubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GithubApiClient {

    private final GithubOAuthProperties githubOAuthProperties;

    public GithubAccessTokenResponse requestAccessToken(String code) {
        return WebClient.create()
                .post()
                .uri("https://github.com/login/oauth/access_token")
                .headers(h -> h.set("Accept", "application/json"))
                .bodyValue(Map.of(
                        "client_id", githubOAuthProperties.getClientId(),
                        "client_secret", githubOAuthProperties.getClientSecret(),
                        "code", code,
                        "redirect_uri", githubOAuthProperties.getRedirectUri()
                ))
                .retrieve()
                .bodyToMono(GithubAccessTokenResponse.class)
                .block();
    }

    public GithubUserResponse requestUserInfo(String accessToken) {
        return WebClient.create()
                .get()
                .uri("https://api.github.com/user")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GithubUserResponse.class)
                .block();
    }
}
