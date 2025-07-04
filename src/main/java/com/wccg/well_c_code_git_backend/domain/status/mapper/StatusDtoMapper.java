package com.wccg.well_c_code_git_backend.domain.status.mapper;

import com.wccg.well_c_code_git_backend.domain.status.dto.controller.response.GetStatusResponse;
import com.wccg.well_c_code_git_backend.domain.status.dto.service.response.ServiceGetStatusResponse;

public final class StatusDtoMapper {

    private StatusDtoMapper() {

    }

    public static GetStatusResponse toGetStatusResponse(ServiceGetStatusResponse serviceGetStatusResponse) {
        return new GetStatusResponse(
                serviceGetStatusResponse.getUserId(),
                serviceGetStatusResponse.getUserGithubLoginId(),
                serviceGetStatusResponse.getName(),
                serviceGetStatusResponse.getProfileImageUrl(),
                serviceGetStatusResponse.getTotalRepository(),
                serviceGetStatusResponse.getTotalStar(),
                serviceGetStatusResponse.getTotalCommit()
        );
    }
}