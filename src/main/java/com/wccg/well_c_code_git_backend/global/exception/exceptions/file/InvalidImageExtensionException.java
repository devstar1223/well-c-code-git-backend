package com.wccg.well_c_code_git_backend.global.exception.exceptions.file;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class InvalidImageExtensionException extends BaseException {
    public InvalidImageExtensionException() {
        super(ErrorCode.INVALID_IMAGE_EXTENSION);
    }
}
