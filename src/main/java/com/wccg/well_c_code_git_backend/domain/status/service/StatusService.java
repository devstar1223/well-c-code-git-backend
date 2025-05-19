package com.wccg.well_c_code_git_backend.domain.status.service;

import com.wccg.well_c_code_git_backend.domain.commit.model.Commit;
import com.wccg.well_c_code_git_backend.domain.commit.service.CommitService;
import com.wccg.well_c_code_git_backend.domain.status.dto.service.response.ServiceGetStatusResponse;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatusService {

    private final WccgRepositoryService wccgRepositoryService;
    private final CommitService commitService;
    private final UserService userService;

    public ServiceGetStatusResponse getStatusBy(Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);

        String name = user.getName();
        String userGithubLoginId = user.getGithubLoginId();
        String profileImageUrl = user.getProfileImageUrl();

        List<WccgRepository> repositories = wccgRepositoryService.getRepositoriesByUserId(userId);

        int totalRepository = repositories.size();
        int totalStar = repositories
                .stream()
                .mapToInt(WccgRepository::getStar)
                .sum();

        List<Commit> commits = commitService.getAllCommitByUserId(userId);
        int totalCommit = commits.size();

        return new ServiceGetStatusResponse(userId, userGithubLoginId, name, profileImageUrl, totalRepository, totalStar, totalCommit);
    }
}