package com.wccg.well_c_code_git_backend.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSaveRequest {
    private final Long githubId;
    private final String githubLoginId;
    private final String name;
    private final String introduce;
    private final String profileImageUrl;
    private final UserRole userRole;
    private final boolean isActive;
}
