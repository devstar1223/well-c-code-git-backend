package com.wccg.well_c_code_git_backend.global.github.client;

import com.wccg.well_c_code_git_backend.global.github.dto.GithubAccessTokenResponse;
import com.wccg.well_c_code_git_backend.global.security.oauth.GithubOAuthProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class GithubOauthClient {

    private final GithubOAuthProperties githubOAuthProperties;
    private final WebClient githubOauthWebClient;

    public GithubOauthClient(
            @Qualifier("githubOauthWebClient") WebClient githubOauthWebClient,
            GithubOAuthProperties githubOAuthProperties
    ) {
        this.githubOauthWebClient = githubOauthWebClient;
        this.githubOAuthProperties = githubOAuthProperties;
    }

    public GithubAccessTokenResponse requestAccessToken(String code) {
        return githubOauthWebClient
                .post()
                .uri("login/oauth/access_token")
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
}
