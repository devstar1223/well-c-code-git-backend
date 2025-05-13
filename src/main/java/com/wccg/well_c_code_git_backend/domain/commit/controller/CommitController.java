package com.wccg.well_c_code_git_backend.domain.commit.controller;

import com.wccg.well_c_code_git_backend.domain.commit.dto.controller.response.SyncCommitResponse;
import com.wccg.well_c_code_git_backend.domain.commit.dto.service.response.ServiceSyncCommitResponse;
import com.wccg.well_c_code_git_backend.domain.commit.service.CommitService;
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

import static com.wccg.well_c_code_git_backend.domain.commit.mapper.CommitDtoMapper.toSyncCommitResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commit")
public class CommitController {

    private final CommitService commitService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    @Operation(
            summary = "이용자 GitHub 커밋 동기화 및 저장",
            description = """
                    **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                    
                    이후, 해당 이용자의 리포지토리와 그에 속한 브랜치 목록을 DB에서 조회한 뒤,
                    각 브랜치에 대해 GitHub API를 호출하여 커밋 정보를 가져옵니다.
                    
                    수집된 커밋 목록은 모두 DB에 저장되며, 저장이 완료된 전체 커밋의 개수를 응답으로 반환합니다.
                    """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<SyncCommitResponse>> syncCommit(@AuthenticationPrincipal User user) {
        ServiceSyncCommitResponse serviceSyncCommitResponse = commitService.syncCommitFor(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toSyncCommitResponse(serviceSyncCommitResponse),"이용자 GitHub 커밋 동기화 및 저장 완료"));
    }
}
