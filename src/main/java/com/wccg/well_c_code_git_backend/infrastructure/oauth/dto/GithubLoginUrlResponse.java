package com.wccg.well_c_code_git_backend.infrastructure.oauth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GithubLoginUrlResponse {
    private final String loginUrl;
}
