package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request;

import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPostStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceUpdateRecruitPostRequest {
    private final Long teamId;
    private final Long recruitPostId;
    private final String title;
    private final String content;
    private final RecruitPostStatus recruitPostStatus;
}