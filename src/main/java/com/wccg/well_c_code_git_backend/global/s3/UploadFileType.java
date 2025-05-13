package com.wccg.well_c_code_git_backend.global.s3;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UploadFileType {
    USER_PROFILE_PHOTO("유저 프로필 사진","user_profile_photo/"),
    TEAM_INFO_PHOTO("팀 정보 사진","team_info_photo/");

    private final String description;
    private final String defaultPrefix;
}
