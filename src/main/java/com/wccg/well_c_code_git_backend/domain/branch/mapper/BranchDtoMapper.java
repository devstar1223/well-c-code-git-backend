package com.wccg.well_c_code_git_backend.domain.branch.mapper;

import com.wccg.well_c_code_git_backend.domain.branch.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.branch.dto.SyncResponse;

public final class BranchDtoMapper {

    private BranchDtoMapper(){

    }

    public static SyncResponse toSyncResponse(ServiceSyncResponse serviceSyncResponse){
        return new SyncResponse(serviceSyncResponse.getBranchCount());
    }

    public static ServiceSyncResponse toServiceSyncResponse(int branchCount){
        return new ServiceSyncResponse(branchCount);
    }
}
