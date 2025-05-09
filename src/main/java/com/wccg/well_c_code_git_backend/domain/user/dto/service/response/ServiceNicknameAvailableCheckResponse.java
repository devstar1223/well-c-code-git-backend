package com.wccg.well_c_code_git_backend.domain.user.dto.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ServiceNicknameAvailableCheckResponse {
    private final boolean isAvaliable;
    private final String nickname;
}
