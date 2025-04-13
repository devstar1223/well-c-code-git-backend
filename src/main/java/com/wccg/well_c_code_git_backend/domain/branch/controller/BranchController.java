package com.wccg.well_c_code_git_backend.domain.branch.controller;

import com.wccg.well_c_code_git_backend.domain.branch.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.branch.dto.SyncResponse;
import com.wccg.well_c_code_git_backend.domain.branch.service.BranchService;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wccg.well_c_code_git_backend.domain.branch.mapper.BranchDtoMapper.toSyncResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchService branchService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    public ResponseEntity<SyncResponse> sync(@AuthenticationPrincipal User user) {

        ServiceSyncResponse serviceResponse = branchService.syncBranchFrom(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toSyncResponse(serviceResponse));
    }
}
