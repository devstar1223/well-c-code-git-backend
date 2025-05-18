package com.wccg.well_c_code_git_backend.domain.team.dto.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class JoinTeamRequestResponse {
    private final String teamName;
    private final String joinStatusDescription;
}
