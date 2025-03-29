package com.wccg.well_c_code_git_backend.infrastructure.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}