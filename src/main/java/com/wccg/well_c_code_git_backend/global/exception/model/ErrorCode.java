package com.wccg.well_c_code_git_backend.global.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E001", "해당 유저를 찾을 수 없습니다."),
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "해당 유저의 활성화된 액세스 토큰이 없습니다."),
    USER_MISMATCH(HttpStatus.FORBIDDEN, "E003", "인증된 사용자 정보와 요청한 사용자 정보가 일치하지 않습니다."),
    FOR_TEST_ERROR(HttpStatus.BAD_REQUEST, "E777", "테스트용 에러 코드");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
