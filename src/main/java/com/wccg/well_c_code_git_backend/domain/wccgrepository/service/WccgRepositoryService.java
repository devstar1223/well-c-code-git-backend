package com.wccg.well_c_code_git_backend.domain.wccgrepository.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.service.AccessTokenService;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.service.response.ServiceGetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.service.response.ServiceSyncWccgRepositoryResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.RepositorySortType;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.SortDirection;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.repository.WccgRepositoryRepository;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.auth.AccessTokenNotFoundException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.user.UserNotFoundException;
import com.wccg.well_c_code_git_backend.global.github.client.GithubRepositoryClient;
import com.wccg.well_c_code_git_backend.global.github.dto.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.wccgrepository.mapper.WccgRepositoryDtoMapper.toServiceGetRepositoriesResponseList;
import static com.wccg.well_c_code_git_backend.domain.wccgrepository.mapper.WccgRepositoryDtoMapper.toServiceSyncResponse;

@Service
@RequiredArgsConstructor
public class WccgRepositoryService {
    private final WccgRepositoryRepository wccgRepositoryRepository;
    private final GithubRepositoryClient githubRepositoryClient;
    private final AccessTokenService accessTokenService;
    private final UserService userService;

    @Transactional
    public ServiceSyncWccgRepositoryResponse syncWccgRepositoryFor(User userPrincipal) {
        User user = getPersistentUser(userPrincipal);

        String accessTokenValue = getAccessTokenValue(user);

        List<GithubRepositoryResponse> githubRepositoryResponseList = githubRepositoryClient.getPublicRepositories(accessTokenValue);

        List<WccgRepository> repos = toWccgRepositories(user, githubRepositoryResponseList);

        wccgRepositoryRepository.saveAll(repos);

        return toServiceSyncResponse(repos.size());
    }

    public List<ServiceGetRepositoriesResponse> getRepositoriesSorted(Long userId, String sortBy, String order) {
        userService.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);
        Sort sort = setSort(sortBy, order);

        List<WccgRepository> wccgRepositoryList = wccgRepositoryRepository.findAllByUserIdAndIsActiveTrue(userId, sort);
        return toServiceGetRepositoriesResponseList(wccgRepositoryList);
    }

    private String getAccessTokenValue(User user) {
        return accessTokenService
                .getActiveAccessTokenByUserId(user.getId())
                .orElseThrow(AccessTokenNotFoundException::new)
                .getAccessToken();
    }

    private User getPersistentUser(User userPrincipal) {
        return userService.getUserById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
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

    private static Sort setSort(String sortBy, String order) {
        RepositorySortType sortType = RepositorySortType.from(sortBy);
        SortDirection sortDirection = SortDirection.from(order);

        Sort.Direction direction = Sort.Direction.valueOf(sortDirection.name());
        return Sort.by(direction, sortType.getPropertyName());
    }

    public List<WccgRepository> getRepositoriesByUserId(Long userId) {
        return wccgRepositoryRepository.findAllByUserIdAndIsActiveTrue(userId);
    }

}
