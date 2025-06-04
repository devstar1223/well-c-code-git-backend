package com.wccg.well_c_code_git_backend.domain.team.dto.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceJoinTeamRequestRequest {
    private final Long teamId;
    private final String joinIntroduce;
}
