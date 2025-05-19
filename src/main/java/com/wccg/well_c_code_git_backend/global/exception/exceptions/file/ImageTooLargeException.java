package com.wccg.well_c_code_git_backend.global.exception.exceptions.file;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class ImageTooLargeException extends BaseException {
    public ImageTooLargeException() {
        super(ErrorCode.IMAGE_TOO_LARGE);
    }
}
