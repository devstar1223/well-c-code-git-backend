package com.wccg.well_c_code_git_backend.domain.recruitpost.controller;

import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.request.CreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.response.CreateRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.ServiceCreateRecruitPostResponse;
import com.wccg.well_c_code_git_backend.domain.recruitpost.service.RecruitPostService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wccg.well_c_code_git_backend.domain.recruitpost.mapper.RecruitPostDtoMapper.toCreateRecruitPostResponse;
import static com.wccg.well_c_code_git_backend.domain.recruitpost.mapper.RecruitPostDtoMapper.toServiceCreateRecruitPostRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruit-post")
public class RecruitPostController {

    private final RecruitPostService recruitPostService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    @Operation(
            summary = "모집 글 작성",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            - 팀의 관리자가 팀원을 모집하는 글을 모집 게시판에 작성합니다.
                            - 팀의 관리자만 모집 글을 작성할 수 있습니다.
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<CreateRecruitPostResponse>> createRecruitPost(@AuthenticationPrincipal User user, @RequestBody CreateRecruitPostRequest request){
        ServiceCreateRecruitPostResponse serviceResponse = recruitPostService.createRecruitPost(user, toServiceCreateRecruitPostRequest(request));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toCreateRecruitPostResponse(serviceResponse),"모집 글 작성 완료"));
    }
}
