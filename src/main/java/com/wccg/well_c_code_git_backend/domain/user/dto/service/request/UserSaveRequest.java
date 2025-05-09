package com.wccg.well_c_code_git_backend.domain.user.dto.service.request;

import com.wccg.well_c_code_git_backend.domain.user.model.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UserSaveRequest {
    private final Long githubId;
    private final String githubLoginId;
    private final String name;
    private final String nickname;
    private final String introduce;
    private final String profileImageUrl;
    private final UserRole userRole;
    private final boolean isActive;
}
