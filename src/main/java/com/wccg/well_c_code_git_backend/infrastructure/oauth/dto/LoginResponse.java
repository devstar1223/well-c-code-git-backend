package com.wccg.well_c_code_git_backend.infrastructure.oauth.dto;

import com.wccg.well_c_code_git_backend.domain.user.User;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final String JwtAccessToken;
    private final Long id;
    private final String githubLoginId;
    private final String name;
    private final String profileImageUrl;

    public LoginResponse(String JwtAccessToken, User user) {
        this.JwtAccessToken = JwtAccessToken;
        this.id = user.getId();
        this.githubLoginId = user.getGithubLoginId();
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
