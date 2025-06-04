package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class CreateRecruitPostRequest {
    private final Long teamId;
    private final String title;
    private final String content;
}
