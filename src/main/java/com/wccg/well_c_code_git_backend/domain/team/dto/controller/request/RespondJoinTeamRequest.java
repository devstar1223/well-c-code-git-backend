package com.wccg.well_c_code_git_backend.domain.team.dto.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class RespondJoinTeamRequest {
    private final Long teamUsersId;
    private final boolean accepted;
}
