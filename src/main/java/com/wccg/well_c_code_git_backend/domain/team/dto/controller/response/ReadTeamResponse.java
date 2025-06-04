package com.wccg.well_c_code_git_backend.domain.team.dto.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@RequiredArgsConstructor
public class ReadTeamResponse {
    private final String name;
    private final String infoImageURL;
    private final String introduce;
    private final int teamMemberCount;
    private final LocalDateTime createdAt;
}
