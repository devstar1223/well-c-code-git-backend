package com.wccg.well_c_code_git_backend.infrastructure.oauth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
public class GithubAccessTokenResponse {

    @JsonProperty("access_token")
    private final String accessToken;
}