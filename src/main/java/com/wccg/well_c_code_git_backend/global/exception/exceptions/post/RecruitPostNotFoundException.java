package com.wccg.well_c_code_git_backend.global.exception.exceptions.post;

import com.wccg.well_c_code_git_backend.global.exception.model.BaseException;
import com.wccg.well_c_code_git_backend.global.exception.model.ErrorCode;

public class RecruitPostNotFoundException extends BaseException {
    public RecruitPostNotFoundException() {
        super(ErrorCode.RECRUIT_POST_NOT_FOUND);
    }
}
