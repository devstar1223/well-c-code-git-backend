package com.wccg.well_c_code_git_backend.domain.wccgrepository.dto;

import com.wccg.well_c_code_git_backend.domain.wccgrepository.WccgRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class ServiceGetRepositoriesResponse {
    private final Long id;
    private final Long userId;

    private final String name;
    private final String owner;
    private final String description;
    private final int star;
    private final String language;
    private final boolean isForked;

    private final LocalDateTime githubCreatedAt;
    private final LocalDateTime githubUpdatedAt;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean isActive;

    public static ServiceGetRepositoriesResponse from(WccgRepository repository) {
        return new ServiceGetRepositoriesResponse(
                repository.getId(),
                repository.getUser().getId(),
                repository.getName(),
                repository.getOwner(),
                repository.getDescription(),
                repository.getStar(),
                repository.getLanguage(),
                repository.isForked(),
                repository.getGithubCreatedAt(),
                repository.getGithubUpdatedAt(),
                repository.getCreatedAt(),
                repository.getUpdatedAt(),
                repository.isActive()
        );
    }

    public static List<ServiceGetRepositoriesResponse> fromList(List<WccgRepository> repositories) {
        return repositories.stream()
                .map(ServiceGetRepositoriesResponse::from)
                .toList();
    }

}
