package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceCreateRecruitPostRequest {
    private final Long teamId;
    private final String title;
    private final String content;
}
