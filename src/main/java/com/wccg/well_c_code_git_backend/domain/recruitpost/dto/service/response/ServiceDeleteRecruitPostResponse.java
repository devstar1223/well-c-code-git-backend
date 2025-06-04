package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response;

import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceDeleteRecruitPostResponse {
    private final RecruitPost recruitPost;
}
