package com.wccg.well_c_code_git_backend.domain.wccgrepository.controller;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.GetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceGetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.SyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.RepositorySortType;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.SortDirection;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.mapper.WccgRepositoryDtoMapper.toGetRepositoriesResponse;
import static com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.mapper.WccgRepositoryDtoMapper.toSyncResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wccgrepository")
public class WccgRepositoryController {

    private final UserService userService;
    private final WccgRepositoryService wccgRepositoryService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    public ResponseEntity<SyncResponse> sync(@AuthenticationPrincipal User user) {
        ServiceSyncResponse serviceResponse = wccgRepositoryService.syncRepositoryFrom(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toSyncResponse(serviceResponse));
    }

    @GetMapping("/repositories")
    public ResponseEntity<List<GetRepositoriesResponse>> getRepositories(
            @RequestParam(name = "sort", defaultValue = "githubCreatedAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order
    ) {
        RepositorySortType sortType = RepositorySortType.from(sortBy);
        SortDirection sortDirection = SortDirection.from(order);

        List<ServiceGetRepositoriesResponse> serviceResponses = wccgRepositoryService.getRepositoriesSorted(sortType, sortDirection);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toGetRepositoriesResponse(serviceResponses));
    }
}
