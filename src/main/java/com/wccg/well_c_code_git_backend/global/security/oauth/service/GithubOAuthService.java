package com.wccg.well_c_code_git_backend.global.security.oauth.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.dto.service.request.AccessTokenSaveRequest;
import com.wccg.well_c_code_git_backend.domain.accesstoken.service.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.model.UserRole;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.UserSaveRequest;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.global.github.client.GithubOauthClient;
import com.wccg.well_c_code_git_backend.global.github.client.GithubUserClient;
import com.wccg.well_c_code_git_backend.global.security.jwt.JwtProvider;
import com.wccg.well_c_code_git_backend.global.security.oauth.GithubOAuthProperties;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubAccessTokenResponse;
import com.wccg.well_c_code_git_backend.global.security.oauth.dto.GithubLoginUrlResponse;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubUserResponse;
import com.wccg.well_c_code_git_backend.global.security.oauth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubOAuthService {

    private final GithubOAuthProperties githubOAuthProperties;
    private final GithubOauthClient githubOauthClient;
    private final GithubUserClient githubUserClient;
    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final JwtProvider jwtProvider;

    public GithubLoginUrlResponse createLoginUrl() {
        String githubAuthorizeUrl = createGithubAuthorizeUrl();
        return new GithubLoginUrlResponse(githubAuthorizeUrl);
    }

    public LoginResponse processGithubCallback(String code) {
        GithubAccessTokenResponse tokenResponse = githubOauthClient.requestAccessToken(code);
        String accessToken = tokenResponse.getAccessToken();

        GithubUserResponse githubUser = githubUserClient.requestUserInfo(accessToken);

        User user = getOrRegisterUserWithSaveToken(githubUser, tokenResponse);

        String jwt = jwtProvider.createToken(user);
        return new LoginResponse(jwt, user);
    }

    private User getOrRegisterUserWithSaveToken(GithubUserResponse githubUser, GithubAccessTokenResponse tokenResponse) {
        return userService.getUserByGithubId(githubUser.getId())
                .map(existingUser -> {
                    accessTokenService.deactivatePreviousTokens(existingUser);
                    saveAccessToken(tokenResponse, existingUser);
                    return existingUser;
                })
                .orElseGet(() -> {
                    User newUser = saveUser(githubUser);
                    saveAccessToken(tokenResponse, newUser);
                    return newUser;
                });
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

    private User saveUser(GithubUserResponse githubUserResponse) {
        UserSaveRequest userSaveRequest = toUserSaveRequest(githubUserResponse);
        return userService.save(userSaveRequest);
    }

    private UserSaveRequest toUserSaveRequest(GithubUserResponse response) {
        return new UserSaveRequest(
                response.getId(),
                response.getLogin(),
                response.getName(),
                // TODO. 닉네임 랜덤 생성으로 변경
                "랜덤한닉네임123",
                response.getBio(),
                response.getAvatarUrl(),
                UserRole.USER,
                true
        );
    }

    private void saveAccessToken(GithubAccessTokenResponse githubAccessTokenResponse, User existingUser) {
        AccessTokenSaveRequest accessTokenSaveRequest = toAccessTokenSaveRequest(githubAccessTokenResponse, existingUser);
        accessTokenService.save(accessTokenSaveRequest);
    }

    private AccessTokenSaveRequest toAccessTokenSaveRequest(GithubAccessTokenResponse response, User user) {
        return new AccessTokenSaveRequest(
                response.getAccessToken(),
                user,
                true
        );
    }
}
