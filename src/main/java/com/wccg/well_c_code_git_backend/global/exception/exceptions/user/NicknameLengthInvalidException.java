package com.wccg.well_c_code_git_backend.global.exception.exceptions.user;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class NicknameLengthInvalidException extends BaseException {
    public NicknameLengthInvalidException() {
        super(ErrorCode.NICKNAME_LENGTH_INVALID);
    }
}
