package com.wccg.well_c_code_git_backend.global.exception.exceptions.team;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class TeamIntroduceTooLongException extends BaseException {
    public TeamIntroduceTooLongException() {
        super(ErrorCode.TEAM_INTRODUCE_TOO_LONG);
    }
}
