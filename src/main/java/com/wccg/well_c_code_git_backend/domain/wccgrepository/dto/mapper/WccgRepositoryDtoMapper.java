package com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.mapper;

import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.GetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceGetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.SyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class WccgRepositoryDtoMapper {

    public static SyncResponse toSyncResponse(ServiceSyncResponse serviceResponse) {
        return new SyncResponse(serviceResponse.getRepositoryCount());
    }

    public static ServiceSyncResponse toServiceSyncResponse(int size) {
        return new ServiceSyncResponse(size);
    }

    public static List<GetRepositoriesResponse> toGetRepositoriesResponse(List<ServiceGetRepositoriesResponse> serviceResponses) {
        List<GetRepositoriesResponse> result = new ArrayList<>();

        for (int i = 0; i < serviceResponses.size(); i++) {
            ServiceGetRepositoriesResponse s = serviceResponses.get(i);
            result.add(new GetRepositoriesResponse(
                    i + 1,
                    s.getId(),
                    s.getUserId(),
                    s.getName(),
                    s.getOwner(),
                    s.getDescription(),
                    s.getStar(),
                    s.getLanguage(),
                    s.isForked(),
                    s.getGithubCreatedAt(),
                    s.getGithubUpdatedAt()
            ));
        }

        return result;
    }

    public static ServiceGetRepositoriesResponse toServiceGetRepositoriesResponse(WccgRepository wccgRepository) {
        return new ServiceGetRepositoriesResponse(
                wccgRepository.getId(),
                wccgRepository.getUser().getId(),
                wccgRepository.getName(),
                wccgRepository.getOwner(),
                wccgRepository.getDescription(),
                wccgRepository.getStar(),
                wccgRepository.getLanguage(),
                wccgRepository.isForked(),
                wccgRepository.getGithubCreatedAt(),
                wccgRepository.getGithubUpdatedAt(),
                wccgRepository.getCreatedAt(),
                wccgRepository.getUpdatedAt(),
                wccgRepository.isActive()
        );
    }

    public static List<ServiceGetRepositoriesResponse> toServiceGetRepositoriesResponseList(List<WccgRepository> repositories) {
        return repositories.stream()
                .map(WccgRepositoryDtoMapper::toServiceGetRepositoriesResponse)
                .toList();
    }
}
