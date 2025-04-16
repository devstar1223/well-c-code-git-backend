package com.wccg.well_c_code_git_backend.domain.commit.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CommitServiceSyncResponse {
    private final int commitCount;
}
