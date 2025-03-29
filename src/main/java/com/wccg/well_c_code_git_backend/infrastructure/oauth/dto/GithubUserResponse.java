package com.wccg.well_c_code_git_backend.infrastructure.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubUserResponse {

    private String login;

    private String name;

    private String bio;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
