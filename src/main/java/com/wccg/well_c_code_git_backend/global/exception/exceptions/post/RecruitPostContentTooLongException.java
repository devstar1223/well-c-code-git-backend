package com.wccg.well_c_code_git_backend.global.exception.exceptions.post;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class RecruitPostContentTooLongException extends BaseException {
    public RecruitPostContentTooLongException() {
        super(ErrorCode.RECRUIT_POST_CONTENT_TOO_LONG);
    }
}
