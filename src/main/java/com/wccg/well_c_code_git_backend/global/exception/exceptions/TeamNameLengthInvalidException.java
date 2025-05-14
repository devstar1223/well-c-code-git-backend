package com.wccg.well_c_code_git_backend.global.exception.exceptions;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class TeamNameLengthInvalidException extends BaseException {
    public TeamNameLengthInvalidException() {
        super(ErrorCode.TEAM_NAME_LENGTH_INVALID);
    }
}
