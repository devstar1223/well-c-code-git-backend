package com.wccg.well_c_code_git_backend.domain.user.dto.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceUpdateProfileRequest {
    private final String nickname;
    private final String introduce;
    private final String profileImageUrl;
}
