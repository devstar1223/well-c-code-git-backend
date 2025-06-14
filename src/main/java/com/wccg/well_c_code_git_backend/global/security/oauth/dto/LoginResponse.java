package com.wccg.well_c_code_git_backend.global.security.oauth.dto;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final String JwtAccessToken;
    private final Long id;
    private final String githubLoginId;
    private final String name;
    private final String nickname;
    private final String profileImageUrl;

    public LoginResponse(String JwtAccessToken, User user) {
        this.JwtAccessToken = JwtAccessToken;
        this.id = user.getId();
        this.githubLoginId = user.getGithubLoginId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
