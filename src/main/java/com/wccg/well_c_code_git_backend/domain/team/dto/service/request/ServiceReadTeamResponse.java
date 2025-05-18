package com.wccg.well_c_code_git_backend.domain.team.dto.service.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceReadTeamResponse {
    private final String name;
    private final String infoImageURL;
    private final String introduce;
    private final int teamMemberCount;
    private final LocalDateTime createdAt;
}
