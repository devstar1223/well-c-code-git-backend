package com.wccg.well_c_code_git_backend.global.exception.exceptions.team;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class TeamJoinRequestNotFound extends BaseException {
    public TeamJoinRequestNotFound() {
        super(ErrorCode.TEAM_JOIN_REQUEST_NOT_FOUND);
    }
}
