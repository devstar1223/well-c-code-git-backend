package com.wccg.well_c_code_git_backend.domain.team.dto.controller.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class UpdateTeamResponse {
    private final Long teamId;
    private final String name;
    private final String introduce;
    private final String infoImageUrl;
    private final LocalDateTime updatedAt;
}
