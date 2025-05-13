package com.wccg.well_c_code_git_backend.domain.branch.controller;

import com.wccg.well_c_code_git_backend.domain.branch.dto.controller.response.SyncBranchResponse;
import com.wccg.well_c_code_git_backend.domain.branch.dto.service.response.ServiceSyncBranchResponse;
import com.wccg.well_c_code_git_backend.domain.branch.service.BranchService;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wccg.well_c_code_git_backend.domain.branch.mapper.BranchDtoMapper.toSyncBranchResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchService branchService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    @Operation(
            summary = "이용자 GitHub 브랜치 동기화 및 저장",
            description = """
                    **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                    
                    요청 헤더에 포함된 JWT를 통해 인증된 이용자의 GitHub 액세스 토큰을 DB에서 조회합니다.
                    
                    이후, 해당 이용자의 리포지토리 목록을 DB에서 조회하고
                    각 리포지토리에 대해 GitHub API를 호출하여 모든 브랜치 정보를 가져옵니다.
                    
                    가져온 브랜치 목록은 모두 DB에 저장되며, 저장이 완료된 전체 브랜치의 개수를 응답으로 반환합니다.
                    """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<SyncBranchResponse>> syncBranch(@AuthenticationPrincipal User user) {

        ServiceSyncBranchResponse serviceResponse = branchService.syncBranchFor(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toSyncBranchResponse(serviceResponse),"이용자 GitHub 브랜치 동기화 및 저장 완료"));
    }
}