package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@RequiredArgsConstructor
public class DeleteRecruitPostResponse {
    private final boolean isActive;
    private final LocalDateTime updatedAt;
}
