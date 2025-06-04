package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response;

import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPostSummary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceReadRecruitPostListResponse {
    private final int totalPages;
    private final long totalElements;
    private final boolean hasNext;
    private final List<RecruitPostSummary> posts;
}
