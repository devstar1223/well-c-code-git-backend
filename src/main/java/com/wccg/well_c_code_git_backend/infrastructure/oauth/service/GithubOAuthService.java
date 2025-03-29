package com.wccg.well_c_code_git_backend.infrastructure.oauth.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessToken;
import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessTokenRepository;
import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.user.UserRepository;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.GithubOAuthProperties;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.GithubAccessTokenResponse;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.GithubLoginUrlResponse;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.GithubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GithubOAuthService {

    private final GithubOAuthProperties githubOAuthProperties;
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;

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

    public void processGithubCallback(String code) {
        String accessToken = requestAccessToken(code);

        GithubUserResponse githubUserResponse = requestUserInfo(accessToken);

        User savedUser = userRepository.save(User.of(
                githubUserResponse.getLogin(),
                githubUserResponse.getName(),
                githubUserResponse.getBio(),
                githubUserResponse.getAvatarUrl(),
                true
        ));

        AccessToken token = AccessToken.of(accessToken, true);
        token.setUser(savedUser);
        accessTokenRepository.save(token);
    }

    private String requestAccessToken(String code) {
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
                .block()
                .getAccessToken();
    }

    private GithubUserResponse requestUserInfo(String accessToken) {
        return WebClient.create()
                .get()
                .uri("https://api.github.com/user")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GithubUserResponse.class)
                .block();
    }

}
