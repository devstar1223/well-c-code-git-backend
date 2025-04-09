package com.wccg.well_c_code_git_backend.domain.wccgrepository;

import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.user.UserService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceGetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.WccgRepositoryServiceSyncResponse;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.UserNotFoundException;
import com.wccg.well_c_code_git_backend.global.github.client.GithubRepositoryClient;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WccgRepositoryService {
    private final WccgRepositoryRepository wccgRepositoryRepository;
    private final GithubRepositoryClient githubRepositoryClient;
    private final AccessTokenService accessTokenService;
    private final UserService userService;

    @Transactional
    public WccgRepositoryServiceSyncResponse syncRepositoryFrom(User userPrincipal) {
        User user = getPersistentUser(userPrincipal);

        String accessToken = accessTokenService.getActiveAccessTokenByUserId(user);
        List<GithubRepositoryResponse> githubRepositoryResponseList = githubRepositoryClient.getPublicRepositories(accessToken);

        List<WccgRepository> repos = toWccgRepositories(user, githubRepositoryResponseList);

        wccgRepositoryRepository.saveAll(repos);

        return new WccgRepositoryServiceSyncResponse(repos.size());
    }

    private User getPersistentUser(User userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
        return user;
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
                            response.getStargazersCount(),
                            response.getLanguage(),
                            true
                    );
                    user.addRepository(repo);
                    return repo;
                })
                .toList();
    }

    public List<ServiceGetRepositoriesResponse> getRepositoriesSorted(RepositorySortType sortType, SortDirection sortDirection) {
        Sort.Direction direction = Sort.Direction.valueOf(sortDirection.name());
        Sort sort = Sort.by(direction, sortType.getPropertyName());

        List<WccgRepository> wccgRepositoryList = wccgRepositoryRepository.findAllByIsActiveTrue(sort);
        return ServiceGetRepositoriesResponse.fromList(wccgRepositoryList);
    }


}
