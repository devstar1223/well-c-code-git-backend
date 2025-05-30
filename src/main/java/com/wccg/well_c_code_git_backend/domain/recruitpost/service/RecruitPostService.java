package com.wccg.well_c_code_git_backend.domain.recruitpost.service;

import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.request.ServiceCreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceCreateRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceReadRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;
import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPostStatus;
import com.wccg.well_c_code_git_backend.domain.recruitpost.repository.RecruitPostRepository;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.post.RecruitPostContentTooLongException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.post.RecruitPostNotFoundException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.post.RecruitPostTitleLengthInvalidException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.team.TeamNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private RecruitPost buildRecruitPost(User user, ServiceCreateRecruitPostRequest serviceRequest) {
        String title = serviceRequest.getTitle();
        if(title.length() < 2 || title.length() > 50){
            throw new RecruitPostTitleLengthInvalidException();
        }

        String content = serviceRequest.getContent();
        if(content.length() > 5000){
            throw new RecruitPostContentTooLongException();
        }

        Long teamId = serviceRequest.getTeamId();
        Team team = teamRepository.findById(teamId)
                        .orElseThrow(TeamNotFoundException::new);

        RecruitPost newPost = RecruitPost.of(title, content, RecruitPostStatus.OPEN, true);

        newPost.setTeam(team);
        newPost.setUser(user);

        return newPost;
    }

    public ServiceReadRecruitPostResponse readRecruitPost(Long recruitPostId) {
        RecruitPost recruitPost = recruitPostRepository.findByIdAndIsActiveTrue(recruitPostId)
                .orElseThrow(RecruitPostNotFoundException::new);

        return toServiceReadRecruitPostResponse(recruitPost);
    }
}
