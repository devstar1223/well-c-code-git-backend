package com.wccg.well_c_code_git_backend.domain.team.dto.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceJoinTeamRequestResponse {
    private final String teamName;
    private final String joinStatusDescription;
}
