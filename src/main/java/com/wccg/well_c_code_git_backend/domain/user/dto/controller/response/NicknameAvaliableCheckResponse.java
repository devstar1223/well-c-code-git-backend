package com.wccg.well_c_code_git_backend.domain.user.dto.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class NicknameAvaliableCheckResponse {
    private final boolean isAvaliable;
    private final String nickname;
}
