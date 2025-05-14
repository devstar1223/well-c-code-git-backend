package com.wccg.well_c_code_git_backend.domain.team.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JoinStatus {
    PENDING("대기중"),
    ACCEPTED("수락"),
    REJECTED("거절");

    private final String description;
}
