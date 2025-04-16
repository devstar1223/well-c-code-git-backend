package com.wccg.well_c_code_git_backend.domain.commit.mapper;

import com.wccg.well_c_code_git_backend.domain.commit.dto.CommitServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.commit.dto.CommitSyncResponse;

public final class CommitDtoMapper {

    private CommitDtoMapper(){

    }

    public static CommitSyncResponse toSyncResponse(CommitServiceSyncResponse commitServiceSyncResponse){
        return new CommitSyncResponse(commitServiceSyncResponse.getCommitCount());
    }

    public static CommitServiceSyncResponse toServiceSyncResponse(int commitCount){
        return new CommitServiceSyncResponse(commitCount);
    }
}
