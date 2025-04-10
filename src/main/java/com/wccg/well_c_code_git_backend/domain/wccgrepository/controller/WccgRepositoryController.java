package com.wccg.well_c_code_git_backend.domain.wccgrepository.controller;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.RepositorySortType;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.SortDirection;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.GetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceGetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.SyncResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wccgrepository")
public class WccgRepositoryController {

    private final UserService userService;
    private final WccgRepositoryService wccgRepositoryService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    public ResponseEntity<SyncResponse> sync(@AuthenticationPrincipal User user) {
        ServiceSyncResponse ServiceResponse = wccgRepositoryService.syncRepositoryFrom(user);

        SyncResponse ControllerResponse = new SyncResponse(ServiceResponse.getRepositoryCount());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ControllerResponse);
    }

    @GetMapping("/repositories")
    public ResponseEntity<List<GetRepositoriesResponse>> getRepositories(
            @RequestParam(name = "sort", defaultValue = "githubCreatedAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order
    ) {
        RepositorySortType sortType = RepositorySortType.from(sortBy);
        SortDirection sortDirection = SortDirection.from(order);

        List<ServiceGetRepositoriesResponse> serviceResponses = wccgRepositoryService.getRepositoriesSorted(sortType, sortDirection);

        List<GetRepositoriesResponse> responses = convertToResponseList(serviceResponses);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    private List<GetRepositoriesResponse> convertToResponseList(List<ServiceGetRepositoriesResponse> serviceResponses) {
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
}
