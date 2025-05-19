package com.wccg.well_c_code_git_backend.global.exception.exceptions.team;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class TeamNameConflictException extends BaseException {
    public TeamNameConflictException() {
        super(ErrorCode.TEAM_NAME_CONFLICT);
    }
}
