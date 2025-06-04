package com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response;

import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.user.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ServiceReadRecruitPostResponse {
    private final RecruitPost recruitPost;
    private final Team team;
    private final User user;
}
