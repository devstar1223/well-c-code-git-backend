package com.wccg.well_c_code_git_backend.domain.recruitpost.mapper;

import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.request.CreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.request.UpdateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.response.*;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request.ServiceCreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request.ServiceUpdateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.*;
import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;
import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPostSummary;

import java.util.List;

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

                new ReadRecruitPostResponse.TeamInfo(
                        serviceResponse.getTeam().getId(),
                        serviceResponse.getTeam().getInfoImageUrl(),
                        serviceResponse.getTeam().getName(),
                        serviceResponse.getTeam().getIntroduce()
                ),

                new ReadRecruitPostResponse.UserInfo(
                        serviceResponse.getUser().getId(),
                        serviceResponse.getUser().getProfileImageUrl(),
                        serviceResponse.getUser().getNickname(),
                        serviceResponse.getUser().getIntroduce()
                ),

                new ReadRecruitPostResponse.PostInfo(
                        serviceResponse.getRecruitPost().getTitle(),
                        serviceResponse.getRecruitPost().getContent(),
                        serviceResponse.getRecruitPost().getRecruitPostStatus(),
                        serviceResponse.getRecruitPost().getCreatedAt(),
                        serviceResponse.getRecruitPost().getUpdatedAt(),
                        serviceResponse.getRecruitPost().isActive()
                )
        );
    }

    public static ServiceUpdateRecruitPostRequest toServiceUpdateRecruitPostRequest(UpdateRecruitPostRequest request){
        return new ServiceUpdateRecruitPostRequest(
                request.getTeamId(),
                request.getRecruitPostId(),
                request.getTitle(),
                request.getContent(),
                request.getRecruitPostStatus()
        );
    }

    public static ServiceUpdateRecruitPostResponse toServiceUpdateRecruitPostResponse(RecruitPost recruitPost){
        return new ServiceUpdateRecruitPostResponse(
                recruitPost,
                recruitPost.getTeam(),
                recruitPost.getUser()
        );
    }

    public static UpdateRecruitPostResponse toUpdateRecruitPostResponse(ServiceUpdateRecruitPostResponse serviceResponse){
        return new UpdateRecruitPostResponse(
                serviceResponse.getRecruitPost().getId(),

                new UpdateRecruitPostResponse.TeamInfo(
                        serviceResponse.getTeam().getId(),
                        serviceResponse.getTeam().getInfoImageUrl(),
                        serviceResponse.getTeam().getName(),
                        serviceResponse.getTeam().getIntroduce()
                ),

                new UpdateRecruitPostResponse.UserInfo(
                        serviceResponse.getUser().getId(),
                        serviceResponse.getUser().getProfileImageUrl(),
                        serviceResponse.getUser().getNickname(),
                        serviceResponse.getUser().getIntroduce()
                ),

                new UpdateRecruitPostResponse.PostInfo(
                        serviceResponse.getRecruitPost().getTitle(),
                        serviceResponse.getRecruitPost().getContent(),
                        serviceResponse.getRecruitPost().getRecruitPostStatus(),
                        serviceResponse.getRecruitPost().getCreatedAt(),
                        serviceResponse.getRecruitPost().getUpdatedAt(),
                        serviceResponse.getRecruitPost().isActive()
                )
        );
    }

    public static DeleteRecruitPostResponse toDeleteRecruitPostResponse(ServiceDeleteRecruitPostResponse serviceResponse){
        return new DeleteRecruitPostResponse(
                serviceResponse.getRecruitPost().isActive(),
                serviceResponse.getRecruitPost().getUpdatedAt()
        );
    }

    public static ServiceDeleteRecruitPostResponse toServiceDeleteRecruitPostResponse(RecruitPost recruitPost){
        return new ServiceDeleteRecruitPostResponse(
                recruitPost
        );
    }

    public static ReadRecruitPostListResponse toReadRecruitPostListResponse(ServiceReadRecruitPostListResponse serviceResponse){
        List<RecruitPostSummary> controllerPosts = serviceResponse.getPosts().stream()
                .map(post -> new RecruitPostSummary(
                        post.getRecruitPostId(),
                        post.getTitle(),
                        post.getAuthor(),
                        post.getCreatedAt()
                ))
                .toList();

        return new ReadRecruitPostListResponse(
                serviceResponse.getTotalPages(),
                serviceResponse.getTotalElements(),
                serviceResponse.isHasNext(),
                controllerPosts
        );
    }
}
