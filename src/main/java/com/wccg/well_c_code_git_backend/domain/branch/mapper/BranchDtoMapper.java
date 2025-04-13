package com.wccg.well_c_code_git_backend.domain.branch.mapper;


import com.wccg.well_c_code_git_backend.domain.branch.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.branch.dto.SyncResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BranchDtoMapper {
    public SyncResponse toSyncResponse(ServiceSyncResponse serviceSyncResponse){
        return new SyncResponse(serviceSyncResponse.getBranchCount());
    }

    public ServiceSyncResponse toServiceSyncResponse(int branchCount){
        return new ServiceSyncResponse(branchCount);
    }
}
