package com.wccg.well_c_code_git_backend.domain.status.dto.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ServiceGetStatusResponse {
    private final Long userId;
    private final String userGithubLoginId;
    private final String name;
    private final String profileImageUrl;
    private final int totalRepository;
    private final int totalStar;
    private final int totalCommit;
}