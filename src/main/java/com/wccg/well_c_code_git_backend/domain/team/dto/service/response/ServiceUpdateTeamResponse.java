package com.wccg.well_c_code_git_backend.domain.team.dto.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceUpdateTeamResponse {
    private final Long teamId;
    private final String name;
    private final String introduce;
    private final String infoImageUrl;
    private final LocalDateTime updatedAt;
}
