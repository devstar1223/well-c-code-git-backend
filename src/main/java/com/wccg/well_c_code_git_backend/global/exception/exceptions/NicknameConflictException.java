package com.wccg.well_c_code_git_backend.global.exception.exceptions;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class NicknameConflictException extends BaseException {
    public NicknameConflictException() {
        super(ErrorCode.NICKNAME_CONFLICT);
    }
}
