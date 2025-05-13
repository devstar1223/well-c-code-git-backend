package com.wccg.well_c_code_git_backend.domain.user.dto.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class UpdateProfileRequest {
    private final String nickname;
    private final String introduce;
    private final String profileImageUrl;
}
