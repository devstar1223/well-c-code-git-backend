package com.wccg.well_c_code_git_backend.global.exception.exceptions;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
