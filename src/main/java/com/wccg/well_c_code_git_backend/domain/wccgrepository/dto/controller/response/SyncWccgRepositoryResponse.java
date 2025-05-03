package com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class SyncWccgRepositoryResponse {
    private final int repositoryCount;
}
