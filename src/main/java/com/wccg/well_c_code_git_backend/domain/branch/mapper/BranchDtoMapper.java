package com.wccg.well_c_code_git_backend.domain.branch.mapper;

import com.wccg.well_c_code_git_backend.domain.branch.dto.service.response.ServiceSyncBranchResponse;
import com.wccg.well_c_code_git_backend.domain.branch.dto.controller.response.SyncBranchResponse;

public final class BranchDtoMapper {

    private BranchDtoMapper(){

    }

    public static SyncBranchResponse toSyncBranchResponse(ServiceSyncBranchResponse serviceResponse){
        return new SyncBranchResponse(serviceResponse.getBranchCount());
    }

    public static ServiceSyncBranchResponse toServiceSyncBranchResponse(int branchCount){
        return new ServiceSyncBranchResponse(branchCount);
    }
}
