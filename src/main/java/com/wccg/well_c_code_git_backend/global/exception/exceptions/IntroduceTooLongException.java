package com.wccg.well_c_code_git_backend.global.exception.exceptions;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class IntroduceTooLongException extends BaseException {
    public IntroduceTooLongException() {
        super(ErrorCode.INTRODUCE_TOO_LONG);
    }
}
