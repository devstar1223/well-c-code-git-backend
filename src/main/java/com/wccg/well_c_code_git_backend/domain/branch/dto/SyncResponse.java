package com.wccg.well_c_code_git_backend.domain.branch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class SyncResponse {
    private final int branchCount;
}