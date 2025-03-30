package com.wccg.well_c_code_git_backend.infrastructure.oauth.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessToken;
import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessTokenRepository;
import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.user.UserRepository;
import com.wccg.well_c_code_git_backend.domain.user.UserService;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.GithubApiClient;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.GithubOAuthProperties;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.GithubLoginUrlResponse;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.GithubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubOAuthService {

    private final GithubOAuthProperties githubOAuthProperties;
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;
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
        String accessToken = githubApiClient.requestAccessToken(code);

        GithubUserResponse githubUserResponse = githubApiClient.requestUserInfo(accessToken);

        User savedUser = userRepository.save(User.of(
                githubUserResponse.getLogin(),
                githubUserResponse.getName(),
                githubUserResponse.getBio(),
                githubUserResponse.getAvatarUrl(),
                true
        ));

        accessTokenService.saveWithUser(accessToken,savedUser);
    }
}
