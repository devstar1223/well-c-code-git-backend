package com.wccg.well_c_code_git_backend.domain.commit.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.service.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.branch.model.Branch;
import com.wccg.well_c_code_git_backend.domain.branch.service.BranchService;
import com.wccg.well_c_code_git_backend.domain.commit.dto.service.response.ServiceSyncCommitResponse;
import com.wccg.well_c_code_git_backend.domain.commit.model.Commit;
import com.wccg.well_c_code_git_backend.domain.commit.repository.CommitRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.auth.AccessTokenNotFoundException;
import com.wccg.well_c_code_git_backend.global.github.client.GithubCommitsClient;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubCommitResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.commit.mapper.CommitDtoMapper.toServiceSyncCommitResponse;

@Service
@RequiredArgsConstructor
public class CommitService {

    private final AccessTokenService accessTokenService;
    private final WccgRepositoryService wccgRepositoryService;
    private final GithubCommitsClient githubCommitsClient;
    private final BranchService branchService;
    private final CommitRepository commitRepository;

    @Transactional
    public ServiceSyncCommitResponse syncCommitFor(User userPrincipal) {
        Long UserId = userPrincipal.getId();
        String owner = userPrincipal.getGithubLoginId();

        String accessTokenValue = getAccessTokenValue(UserId);

        List<WccgRepository> repositories = wccgRepositoryService.getRepositoriesByUserId(UserId);

        int count = syncCommitsForAllRepositories(repositories, accessTokenValue, owner);

        return toServiceSyncCommitResponse(count);
    }

    public List<Commit> getAllCommitByUserId(Long userId){
        return commitRepository.findAllByUserIdAndIsActiveTrue(userId);
    }

    private int syncCommitsForAllRepositories(List<WccgRepository> repositories, String accessToken, String owner) {
        int count = 0;
        for (WccgRepository repo : repositories) {
            List<Branch> branches = branchService.getAllBranchByWccgRepositoryId(repo.getId());
            for (Branch branch : branches) {
                List<GithubCommitResponse> responses = githubCommitsClient.getCommits(accessToken, owner, repo.getName(), branch.getName());
                count += saveCommits(branch, responses, owner);
            }
        }
        return count;
    }

    private int saveCommits(Branch branch, List<GithubCommitResponse> responses, String owner) {
        int count = 0;
        for (GithubCommitResponse response : responses) {
            if (!response.getAuthor().getLogin().equals(owner)){
                continue;
            }
            Commit commit = getCommit(response);
            branch.addCommit(commit);
            branch.getWccgRepository().getUser().addCommit(commit);
            commitRepository.save(commit);
            count++;
        }
        return count;
    }

    private Commit getCommit(GithubCommitResponse response) {
        String message = response.getCommit().getMessage();
        if (message.length() > 255) {
            message = message.substring(0, 255);
        }

        return Commit.of(
                message,
                response.getSha(),
                response.getCommit().getAuthor().getDate().toLocalDateTime(),
                true);
    }

    private String getAccessTokenValue(Long UserId) {
        return accessTokenService
                .getActiveAccessTokenByUserId(UserId)
                .orElseThrow(AccessTokenNotFoundException::new)
                .getAccessToken();
    }
}
