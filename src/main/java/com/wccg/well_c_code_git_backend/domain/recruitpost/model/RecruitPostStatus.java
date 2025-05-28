package com.wccg.well_c_code_git_backend.domain.recruitpost.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RecruitPostStatus {
    OPEN("모집 중"),
    CLOSED("모집 마감");

    private final String description;
}
