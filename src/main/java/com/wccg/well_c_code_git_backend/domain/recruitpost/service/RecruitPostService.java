package com.wccg.well_c_code_git_backend.domain.recruitpost.service;

import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request.ServiceCreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request.ServiceUpdateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceCreateRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceDeleteRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceReadRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceUpdateRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;
import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPostStatus;
import com.wccg.well_c_code_git_backend.domain.recruitpost.repository.RecruitPostRepository;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.post.RecruitPostContentTooLongException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.post.RecruitPostForbiddenException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.post.RecruitPostNotFoundException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.post.RecruitPostTitleLengthInvalidException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.team.TeamNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.wccg.well_c_code_git_backend.domain.recruitpost.mapper.RecruitPostDtoMapper.*;

@Service
@RequiredArgsConstructor
public class RecruitPostService {

    private final TeamRepository teamRepository;
    private final RecruitPostRepository recruitPostRepository;

    @Transactional
    public ServiceCreateRecruitPostResponse createRecruitPost(User user, ServiceCreateRecruitPostRequest serviceRequest) {
        RecruitPost newPost = buildRecruitPost(user,serviceRequest);
        recruitPostRepository.save(newPost);
        return toServiceCreateRecruitPostResponse(newPost);
    }

    public ServiceReadRecruitPostResponse readRecruitPost(Long recruitPostId) {
        RecruitPost recruitPost = findRecruitPost(recruitPostId);

        return toServiceReadRecruitPostResponse(recruitPost);
    }

    @Transactional
    public ServiceUpdateRecruitPostResponse updateRecruitPost(User user, ServiceUpdateRecruitPostRequest serviceUpdateRecruitPostRequest) {
        RecruitPost recruitPost = findRecruitPost(serviceUpdateRecruitPostRequest.getRecruitPostId());

        recruitPostOwnerValidate(user, recruitPost);
        titleLengthValidate(recruitPost.getTitle());
        contentLengthValidate(recruitPost.getContent());

        updateRecruitPost(serviceUpdateRecruitPostRequest, recruitPost);

        recruitPostRepository.save(recruitPost);

        return toServiceUpdateRecruitPostResponse(recruitPost);
    }

    @Transactional
    public ServiceDeleteRecruitPostResponse deleteRecruitPost(User user, Long recruitPostId) {
        RecruitPost recruitPost = findRecruitPost(recruitPostId);
        recruitPostOwnerValidate(user, recruitPost);
        recruitPost.deleteRecruitPost();
        recruitPostRepository.save(recruitPost);
        return toServiceDeleteRecruitPostResponse(recruitPost);
    }

    private void updateRecruitPost(ServiceUpdateRecruitPostRequest serviceUpdateRecruitPostRequest, RecruitPost updatePost) {
        updatePost.updateRecruitPost(serviceUpdateRecruitPostRequest.getTitle(), serviceUpdateRecruitPostRequest.getContent(), serviceUpdateRecruitPostRequest.getRecruitPostStatus());
    }

    private void recruitPostOwnerValidate(User user, RecruitPost updatePost) {
        if(!Objects.equals(updatePost.getUser().getId(), user.getId())){
            throw new RecruitPostForbiddenException();
        }
    }

    private RecruitPost findRecruitPost(Long recruitPostId) {
        return recruitPostRepository.findByIdAndIsActiveTrue(recruitPostId)
                .orElseThrow(RecruitPostNotFoundException::new);
    }

    private RecruitPost buildRecruitPost(User user, ServiceCreateRecruitPostRequest serviceRequest) {
        String title = serviceRequest.getTitle();
        titleLengthValidate(title);

        String content = serviceRequest.getContent();
        contentLengthValidate(content);

        Long teamId = serviceRequest.getTeamId();
        Team team = teamRepository.findById(teamId)
                        .orElseThrow(TeamNotFoundException::new);

        RecruitPost newPost = RecruitPost.of(title, content, RecruitPostStatus.OPEN, true);

        newPost.setTeam(team);
        newPost.setUser(user);

        return newPost;
    }

    private void contentLengthValidate(String content) {
        if(content.length() > 5000){
            throw new RecruitPostContentTooLongException();
        }
    }

    private void titleLengthValidate(String title) {
        if(title.length() < 2 || title.length() > 50){
            throw new RecruitPostTitleLengthInvalidException();
        }
    }
}
