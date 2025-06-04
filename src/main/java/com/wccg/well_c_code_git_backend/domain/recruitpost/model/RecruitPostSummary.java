package com.wccg.well_c_code_git_backend.domain.recruitpost.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@RequiredArgsConstructor
public class RecruitPostSummary {
    private final Long recruitPostId;
    private final String title;
    private final String author;
    private final LocalDateTime createdAt;
}
