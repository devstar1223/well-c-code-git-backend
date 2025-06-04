package com.wccg.well_c_code_git_backend.domain.team.dto.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ReadJoinTeamRequestResponse {
    private final Long userId;
    private final String nickname;
    private final String joinIntroduce;
    private final String profileImageUrl;
    private final String joinStatusDescription;
}
