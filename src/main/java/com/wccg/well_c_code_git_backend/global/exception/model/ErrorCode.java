package com.wccg.well_c_code_git_backend.global.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E001", "해당 유저를 찾을 수 없습니다."),
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "해당 유저의 활성화된 액세스 토큰이 없습니다."),
    USER_MISMATCH(HttpStatus.FORBIDDEN, "E003", "인증된 사용자 정보와 요청한 사용자 정보가 일치하지 않습니다."),
    INVALID_SORT_PARAMETER(HttpStatus.BAD_REQUEST, "E004", "해당 정렬 타입이 존재하지 않습니다."),
    NICKNAME_CONFLICT(HttpStatus.CONFLICT,"E005","중복된 닉네임 입니다."),
    NICKNAME_LENGTH_INVALID(HttpStatus.BAD_REQUEST, "E006", "닉네임은 2글자 이상 12글자 이하만 가능합니다."),
    S3_FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "E007", "S3 파일 업로드 중 오류가 발생했습니다."),
    INVALID_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, "E008", "허용되지 않은 파일 형식입니다. (jpg, png, webp만 가능)"),
    IMAGE_TOO_LARGE(HttpStatus.BAD_REQUEST, "E009", "파일 크기가 15MB를 초과했습니다."),
    INVALID_IMAGE_DIMENSIONS(HttpStatus.BAD_REQUEST, "E010", "이미지 크기는 반드시 100*100 이어야 합니다."),
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
