package com.wccg.well_c_code_git_backend.infrastructure.oauth.dto;

import com.wccg.well_c_code_git_backend.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessTokenSaveRequest {
    private final String accessToken;
    private final User user;
    private final boolean isActive;
}
