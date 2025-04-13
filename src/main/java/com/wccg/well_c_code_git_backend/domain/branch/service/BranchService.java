package com.wccg.well_c_code_git_backend.domain.branch.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.service.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.branch.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.branch.model.Branch;
import com.wccg.well_c_code_git_backend.domain.branch.repository.BranchRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.UserNotFoundException;
import com.wccg.well_c_code_git_backend.global.github.client.GithubBranchClient;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubBranchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.branch.mapper.BranchDtoMapper.toServiceSyncResponse;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final AccessTokenService accessTokenService;
    private final GithubBranchClient githubBranchClient;
    private final WccgRepositoryService wccgRepositoryService;
    private final UserService userService;
    private final BranchRepository branchRepository;

    @Transactional
    public ServiceSyncResponse SyncBranchFrom(User userPrincipal) {
        User user = getPersistentUser(userPrincipal);

        String accessToken = accessTokenService.getActiveAccessTokenByUserId(user);
        String owner = user.getGithubLoginId();

        List<WccgRepository> repositories = wccgRepositoryService.getRepositoriesByUserId(user.getId());

        int count = 0;

        for (WccgRepository repo : repositories) {
            List<GithubBranchResponse> branches = githubBranchClient.getBranches(accessToken, owner, repo.getName());
            for (GithubBranchResponse response : branches) {
                Branch newBranch = Branch.of(response.getName(), true);
                repo.addBranch(newBranch);
                branchRepository.save(newBranch);
                count++;
            }
        }

        return toServiceSyncResponse(count);
    }

    private User getPersistentUser(User userPrincipal) {
        return userService.getUserById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
    }
}
