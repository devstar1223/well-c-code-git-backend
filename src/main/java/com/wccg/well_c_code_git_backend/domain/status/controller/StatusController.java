package com.wccg.well_c_code_git_backend.domain.status.controller;

import com.wccg.well_c_code_git_backend.domain.status.dto.service.response.ServiceGetStatusResponse;
import com.wccg.well_c_code_git_backend.domain.status.service.StatusService;
import com.wccg.well_c_code_git_backend.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.wccg.well_c_code_git_backend.domain.status.mapper.StatusDtoMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/")
    @Operation(
            summary = "이용자 GitHub 스탯 조회",
            description = """
                    요청한 사용자의 ID(`userId`)를 기반으로 GitHub 관련 통계 정보를 조회합니다.

                    ###조회 항목
                    - 사용자 기본 정보 (이름, GitHub 로그인 ID, 프로필 이미지 URL)
                    - 총 리포지토리 개수
                    - 모든 리포지토리의 총 스타(star) 개수
                    - 총 커밋(commit) 개수
                    """
    )
    public ResponseEntity<ApiResponse> getStatus(
            @RequestParam(name = "userId") Long userId) {

        ServiceGetStatusResponse serviceGetStatusResponse = statusService.getStatusBy(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toGetStatusResponse(serviceGetStatusResponse), "이용자 GitHub 스탯 출력 완료"));
    }
}