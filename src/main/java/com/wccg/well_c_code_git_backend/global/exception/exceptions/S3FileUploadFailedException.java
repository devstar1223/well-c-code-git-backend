package com.wccg.well_c_code_git_backend.global.exception.exceptions;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class S3FileUploadFailedException extends BaseException {
    public S3FileUploadFailedException() {
        super(ErrorCode.S3_FILE_UPLOAD_FAILED);
    }
}
