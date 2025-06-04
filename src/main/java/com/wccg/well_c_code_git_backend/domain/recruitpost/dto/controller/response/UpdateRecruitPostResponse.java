package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.response;

import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPostStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@RequiredArgsConstructor
public class UpdateRecruitPostResponse {
    private final Long postId;
    private final TeamInfo teamInfo;
    private final UserInfo userInfo;
    private final PostInfo postInfo;

    @Getter
    @RequiredArgsConstructor
    @ToString
    public static class TeamInfo {
        private final Long Id;
        private final String infoImageUrl;
        private final String name;
        private final String introduce;
    }

    @Getter
    @RequiredArgsConstructor
    @ToString
    public static class UserInfo {
        private final Long id;
        private final String profileImageUrl;
        private final String nickname;
        private final String introduce;
    }

    @Getter
    @RequiredArgsConstructor
    @ToString
    public static class PostInfo {
        private final String title;
        private final String content;
        private final RecruitPostStatus recruitPostStatus;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final boolean isActive;
    }
}
