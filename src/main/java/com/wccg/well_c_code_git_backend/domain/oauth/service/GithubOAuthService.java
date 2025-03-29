package com.wccg.well_c_code_git_backend.domain.oauth.service;

import com.wccg.well_c_code_git_backend.domain.oauth.GithubOAuthProperties;
import com.wccg.well_c_code_git_backend.domain.oauth.dto.GithubLoginUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubOAuthService {

    private final GithubOAuthProperties githubOAuthProperties;

    public GithubLoginUrlResponse generateLoginUrl() {
        String githubAuthorizeUrl = createGithubAuthorizeUrl();
        return new GithubLoginUrlResponse(githubAuthorizeUrl);
    }

    private String createGithubAuthorizeUrl() {
        String clientId = githubOAuthProperties.getClientId();
        String redirectUri = githubOAuthProperties.getRedirectUri();
        String scope = githubOAuthProperties.getScope();

        return "https://github.com/login/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=" + scope;
    }
}
