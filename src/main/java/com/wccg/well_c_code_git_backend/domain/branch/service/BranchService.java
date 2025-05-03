package com.wccg.well_c_code_git_backend.domain.branch.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.service.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.branch.dto.service.response.ServiceSyncBranchResponse;
import com.wccg.well_c_code_git_backend.domain.branch.model.Branch;
import com.wccg.well_c_code_git_backend.domain.branch.repository.BranchRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.AccessTokenNotFoundException;
import com.wccg.well_c_code_git_backend.global.github.client.GithubBranchClient;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubBranchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.branch.mapper.BranchDtoMapper.toServiceSyncBranchResponse;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final AccessTokenService accessTokenService;
    private final GithubBranchClient githubBranchClient;
    private final WccgRepositoryService wccgRepositoryService;
    private final BranchRepository branchRepository;

    @Transactional
    public ServiceSyncBranchResponse syncBranchFor(User userPrincipal) {
        Long UserId = userPrincipal.getId();
        String owner = userPrincipal.getGithubLoginId();

        String accessTokenValue = getAccessTokenValue(UserId);

        List<WccgRepository> repositories = wccgRepositoryService.getRepositoriesByUserId(UserId);

        int branchCount = getBranchCountFromRepositories(repositories, owner, accessTokenValue);

        return toServiceSyncBranchResponse(branchCount);
    }

    private int getBranchCountFromRepositories(List<WccgRepository> repositories, String owner, String accessTokenValue) {
        return repositories.stream()
                .mapToInt(repo -> syncBranchesForRepository(repo, owner, accessTokenValue))
                .sum();
    }

    private String getAccessTokenValue(Long UserId) {
        return accessTokenService
                .getActiveAccessTokenByUserId(UserId)
                .orElseThrow(AccessTokenNotFoundException::new)
                .getAccessToken();
    }

    private int syncBranchesForRepository(WccgRepository repo, String owner, String accessToken) {
        List<GithubBranchResponse> githubBranches = githubBranchClient.getBranches(accessToken, owner, repo.getName());

        githubBranches.forEach(branchResponse -> {
            Branch newBranch = Branch.of(branchResponse.getName(), true);
            repo.addBranch(newBranch);
            branchRepository.save(newBranch);
        });

        return githubBranches.size();
    }

    public List<Branch> getAllBranchByWccgRepositoryId(Long id){
        return branchRepository.findAllByWccgRepositoryIdAndIsActiveTrue(id);
    }
}
