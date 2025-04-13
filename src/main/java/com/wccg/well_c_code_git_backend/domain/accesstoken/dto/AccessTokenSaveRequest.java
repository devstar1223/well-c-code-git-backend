package com.wccg.well_c_code_git_backend.domain.accesstoken.dto;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class AccessTokenSaveRequest {
    private final String accessToken;
    private final User user;
    private final boolean isActive;
}
