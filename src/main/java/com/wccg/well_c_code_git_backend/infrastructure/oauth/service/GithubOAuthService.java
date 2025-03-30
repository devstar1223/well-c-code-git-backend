package com.wccg.well_c_code_git_backend.infrastructure.oauth.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessTokenSaveRequest;
import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.user.UserSaveRequest;
import com.wccg.well_c_code_git_backend.domain.user.UserService;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.GithubApiClient;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.GithubOAuthProperties;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubOAuthService {

    private final GithubOAuthProperties githubOAuthProperties;
    private final GithubApiClient githubApiClient;
    private final UserService userService;
    private final AccessTokenService accessTokenService;

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
        GithubAccessTokenResponse githubAccessTokenResponse = githubApiClient.requestAccessToken(code);
        String accessToken = githubAccessTokenResponse.getAccessToken();

        GithubUserResponse githubUserResponse = githubApiClient.requestUserInfo(accessToken);
        UserSaveRequest userSaveRequest = toUserSaveRequest(githubUserResponse);

        User savedUser = userService.save(userSaveRequest);

        AccessTokenSaveRequest accessTokenSaveRequest = toAccessTokenSaveRequest(githubAccessTokenResponse,savedUser);

        accessTokenService.save(accessTokenSaveRequest);
    }

    private UserSaveRequest toUserSaveRequest(GithubUserResponse response) {
        return new UserSaveRequest(
                response.getId(),
                response.getLogin(),
                response.getName(),
                response.getBio(),
                response.getAvatarUrl(),
                true
        );
    }

    private AccessTokenSaveRequest toAccessTokenSaveRequest(GithubAccessTokenResponse response, User user){
        return new AccessTokenSaveRequest(
                response.getAccessToken(),
                user,
                true
        );
    }
}
