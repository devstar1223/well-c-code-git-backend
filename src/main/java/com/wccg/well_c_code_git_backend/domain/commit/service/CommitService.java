package com.wccg.well_c_code_git_backend.domain.commit.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.service.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.branch.model.Branch;
import com.wccg.well_c_code_git_backend.domain.branch.service.BranchService;
import com.wccg.well_c_code_git_backend.domain.commit.dto.CommitServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.commit.model.Commit;
import com.wccg.well_c_code_git_backend.domain.commit.repository.CommitRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.AccessTokenNotFoundException;
import com.wccg.well_c_code_git_backend.global.github.client.GithubCommitsClient;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubCommitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.commit.mapper.CommitDtoMapper.toServiceSyncResponse;

@Service
@RequiredArgsConstructor
public class CommitService {

    private final AccessTokenService accessTokenService;
    private final WccgRepositoryService wccgRepositoryService;
    private final GithubCommitsClient githubCommitsClient;
    private final BranchService branchService;
    private final CommitRepository commitRepository;

    public CommitServiceSyncResponse syncCommitFrom(User userPrincipal) {
        Long UserId = userPrincipal.getId();
        String owner = userPrincipal.getGithubLoginId();

        String accessTokenValue = getAccessTokenValue(UserId);

        List<WccgRepository> repositories = wccgRepositoryService.getRepositoriesByUserId(UserId);

        int count = 0;

        for (WccgRepository repo : repositories) {
            List<Branch> branches = branchService.getAllBranchByWccgRepositoryId(repo.getId());
            for (Branch branch : branches) {
                List<GithubCommitResponse> list = githubCommitsClient.getCommits(accessTokenValue, owner, repo.getName(), branch.getName());
                for (GithubCommitResponse response : list) {
                    if (!response.getAuthor().getLogin().equals(owner)) {
                        continue;
                    }
                    Commit commit = Commit.of(
                            response.getCommit().getMessage(),
                            response.getSha(),
                            response.getCommit().getAuthor().getDate().toLocalDateTime(),
                            true);
                    commitRepository.save(commit);
                    count++;
                }
            }
        }

        return toServiceSyncResponse(count);
    }

    private String getAccessTokenValue(Long UserId) {
        return accessTokenService
                .getActiveAccessTokenByUserId(UserId)
                .orElseThrow(AccessTokenNotFoundException::new)
                .getAccessToken();
    }
}
