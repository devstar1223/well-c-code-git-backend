package com.wccg.well_c_code_git_backend.domain.wccgrepository.controller;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.GetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceGetRepositoriesResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.ServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.SyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.service.WccgRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.wccgrepository.mapper.WccgRepositoryDtoMapper.toGetRepositoriesResponse;
import static com.wccg.well_c_code_git_backend.domain.wccgrepository.mapper.WccgRepositoryDtoMapper.toSyncResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wccgrepository")
public class WccgRepositoryController {

    private final WccgRepositoryService wccgRepositoryService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    @Operation(
            summary = "이용자 GitHub 리포지토리 동기화 및 저장",
            description = """
                    **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                    
                    요청 헤더에 포함된 JWT를 통해 인증된 이용자의 GitHub 액세스 토큰을 DB에서 조회합니다.
                    
                    이후, 해당 액세스 토큰을 사용하여 GitHub API로부터 이용자의 공개 리포지토리 목록을 가져옵니다.
                    
                    가져온 리포지토리 목록은 모두 DB에 저장되며, 저장이 완료된 전체 리포지토리의 개수를 응답으로 반환합니다.
                    """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<SyncResponse> sync(@AuthenticationPrincipal User user) {
        ServiceSyncResponse serviceResponse = wccgRepositoryService.syncRepositoryFrom(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toSyncResponse(serviceResponse));
    }


    @GetMapping("/repositories")
    @Operation(
            summary = "이용자의 리포지토리 목록 조회",
            description = """
                    지정한 이용자(`userId`)의 GitHub 리포지토리 정보를 DB에서 조회합니다.
                    
                    ### 정렬 기준
                    - `sort` 파라미터로 정렬 기준을 설정할 수 있습니다.
                      - 사용 가능한 값: 
                        - `githubCreatedAt` : 리포지토리 생성일 기준
                        - `githubUpdatedAt` : 마지막 업데이트일 기준
                        - `star` : 스타 수 기준
                    
                    - `order` 파라미터로 정렬 방향을 설정할 수 있습니다.
                      - 사용 가능한 값:
                        - `asc` : 오름차순
                        - `desc` : 내림차순
                    
                    ### 예시
                    `/repositories?userId=1&sort=star&order=desc`  
                    → 스타 수 기준 내림차순으로 정렬된 리포지토리 목록을 반환합니다.
                    """
    )
    public ResponseEntity<List<GetRepositoriesResponse>> getRepositories(
            @RequestParam(name = "sort", defaultValue = "githubCreatedAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order,
            @RequestParam(name = "userId") Long userId
    ) {
        List<ServiceGetRepositoriesResponse> serviceResponses = wccgRepositoryService.getRepositoriesSorted(userId, sortBy, order);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toGetRepositoriesResponse(serviceResponses));
    }
}
