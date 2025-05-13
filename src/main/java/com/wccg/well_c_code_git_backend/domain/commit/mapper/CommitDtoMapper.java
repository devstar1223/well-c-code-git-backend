package com.wccg.well_c_code_git_backend.domain.commit.mapper;

import com.wccg.well_c_code_git_backend.domain.commit.dto.service.response.ServiceSyncCommitResponse;
import com.wccg.well_c_code_git_backend.domain.commit.dto.controller.response.SyncCommitResponse;

public final class CommitDtoMapper {

    private CommitDtoMapper(){

    }

    public static SyncCommitResponse toSyncCommitResponse(ServiceSyncCommitResponse serviceSyncCommitResponse){
        return new SyncCommitResponse(serviceSyncCommitResponse.getCommitCount());
    }

    public static ServiceSyncCommitResponse toServiceSyncCommitResponse(int commitCount){
        return new ServiceSyncCommitResponse(commitCount);
    }
}
