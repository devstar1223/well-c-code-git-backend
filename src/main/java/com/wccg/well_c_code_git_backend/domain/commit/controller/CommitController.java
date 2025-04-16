package com.wccg.well_c_code_git_backend.domain.commit.controller;

import com.wccg.well_c_code_git_backend.domain.commit.dto.CommitServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.commit.dto.CommitSyncResponse;
import com.wccg.well_c_code_git_backend.domain.commit.service.CommitService;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wccg.well_c_code_git_backend.domain.commit.mapper.CommitDtoMapper.toSyncResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commit")
public class CommitController {

    private final CommitService commitService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    public ResponseEntity<CommitSyncResponse> sync(@AuthenticationPrincipal User user) {
        CommitServiceSyncResponse commitServiceSyncResponse = commitService.syncCommitFrom(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toSyncResponse(commitServiceSyncResponse));
    }
}
