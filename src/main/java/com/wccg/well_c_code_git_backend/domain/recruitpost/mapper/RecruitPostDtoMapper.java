package com.wccg.well_c_code_git_backend.domain.recruitpost.mapper;

import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.request.CreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.response.CreateRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.response.ReadRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request.ServiceCreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceCreateRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceReadRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;

public final class RecruitPostDtoMapper {
    private RecruitPostDtoMapper(){

    }

    public static ServiceCreateRecruitPostRequest toServiceCreateRecruitPostRequest(CreateRecruitPostRequest request){
        return new ServiceCreateRecruitPostRequest(
                request.getTeamId(),
                request.getTitle(),
                request.getContent()
        );
    }

    public static ServiceCreateRecruitPostResponse toServiceCreateRecruitPostResponse(RecruitPost recruitPost){
        return new ServiceCreateRecruitPostResponse(
                recruitPost,
                recruitPost.getTeam(),
                recruitPost.getUser()
        );
    }

    public static CreateRecruitPostResponse toCreateRecruitPostResponse(ServiceCreateRecruitPostResponse serviceResponse){
        return new CreateRecruitPostResponse(
                serviceResponse.getRecruitPost().getId(),

                new CreateRecruitPostResponse.TeamInfo(
                        serviceResponse.getTeam().getId(),
                        serviceResponse.getTeam().getInfoImageUrl(),
                        serviceResponse.getTeam().getName(),
                        serviceResponse.getTeam().getIntroduce()
                ),

                new CreateRecruitPostResponse.UserInfo(
                        serviceResponse.getUser().getId(),
                        serviceResponse.getUser().getProfileImageUrl(),
                        serviceResponse.getUser().getNickname(),
                        serviceResponse.getUser().getIntroduce()
                ),

                new CreateRecruitPostResponse.PostInfo(
                        serviceResponse.getRecruitPost().getTitle(),
                        serviceResponse.getRecruitPost().getContent(),
                        serviceResponse.getRecruitPost().getRecruitPostStatus(),
                        serviceResponse.getRecruitPost().getCreatedAt(),
                        serviceResponse.getRecruitPost().getUpdatedAt(),
                        serviceResponse.getRecruitPost().isActive()
                )
        );
    }

    public static ServiceReadRecruitPostResponse toServiceReadRecruitPostResponse(RecruitPost recruitPost){
        return new ServiceReadRecruitPostResponse(
                recruitPost,
                recruitPost.getTeam(),
                recruitPost.getUser()
        );
    }

    public static ReadRecruitPostResponse toReadRecruitPostResponse(ServiceReadRecruitPostResponse serviceResponse){
        return new ReadRecruitPostResponse(
                serviceResponse.getRecruitPost().getId(),

                new CreateRecruitPostResponse.TeamInfo(
                        serviceResponse.getTeam().getId(),
                        serviceResponse.getTeam().getInfoImageUrl(),
                        serviceResponse.getTeam().getName(),
                        serviceResponse.getTeam().getIntroduce()
                ),

                new CreateRecruitPostResponse.UserInfo(
                        serviceResponse.getUser().getId(),
                        serviceResponse.getUser().getProfileImageUrl(),
                        serviceResponse.getUser().getNickname(),
                        serviceResponse.getUser().getIntroduce()
                ),

                new CreateRecruitPostResponse.PostInfo(
                        serviceResponse.getRecruitPost().getTitle(),
                        serviceResponse.getRecruitPost().getContent(),
                        serviceResponse.getRecruitPost().getRecruitPostStatus(),
                        serviceResponse.getRecruitPost().getCreatedAt(),
                        serviceResponse.getRecruitPost().getUpdatedAt(),
                        serviceResponse.getRecruitPost().isActive()
                )
        );
    }
}
