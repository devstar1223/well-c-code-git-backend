package com.wccg.well_c_code_git_backend.domain.wccgrepository.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class WccgRepositoryServiceSyncResponse {
    private final int repositoryCount;
}
