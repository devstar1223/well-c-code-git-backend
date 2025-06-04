package com.wccg.well_c_code_git_backend.global.exception.exceptions.etc;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;
import lombok.Getter;

@Getter
public class ForTestErrorException extends BaseException {
    public ForTestErrorException() {
        super(ErrorCode.FOR_TEST_ERROR);
    }
}
