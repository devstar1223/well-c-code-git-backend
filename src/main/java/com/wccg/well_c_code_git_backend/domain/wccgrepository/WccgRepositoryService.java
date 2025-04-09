package com.wccg.well_c_code_git_backend.domain.wccgrepository;

import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.WccgRepositoryServiceSyncResponse;
import com.wccg.well_c_code_git_backend.global.github.client.GithubRepositoryClient;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WccgRepositoryService {
    private final WccgRepositoryRepository wccgRepositoryRepository;
    private final GithubRepositoryClient githubRepositoryClient;
    private final AccessTokenService accessTokenService;

    @Transactional
    public WccgRepositoryServiceSyncResponse syncRepositoryFrom(Long userId, User user) {

        String accessToken = accessTokenService.getActiveAccessTokenByUserId(userId);
        List<GithubRepositoryResponse> githubRepositoryResponseList = githubRepositoryClient.getPublicRepositories(accessToken);

        List<WccgRepository> repos = toWccgRepositories(user, githubRepositoryResponseList);

        wccgRepositoryRepository.saveAll(repos);

        return new WccgRepositoryServiceSyncResponse(repos.size());
    }

    private static List<WccgRepository> toWccgRepositories(User user, List<GithubRepositoryResponse> githubRepositoryResponseList) {
        return githubRepositoryResponseList.stream()
                .map(response -> {
                    WccgRepository repo = WccgRepository.of(
                            response.getName(),
                            response.getOwner().getLogin(),
                            response.getDescription(),
                            response.getCreatedAt().toLocalDateTime(),
                            response.getUpdatedAt().toLocalDateTime(),
                            response.isFork(),
                            true);
                    repo.setUser(user);
                    return repo;
                })
                .toList();
    }
}
