package com.wccg.well_c_code_git_backend.global.exception.exceptions;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class InvalidImageDimensions extends BaseException {
    public InvalidImageDimensions() {
        super(ErrorCode.INVALID_IMAGE_DIMENSIONS);
    }
}
