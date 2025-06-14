package com.wccg.well_c_code_git_backend.domain.recruitpost.controller;

import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.request.CreateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.request.UpdateRecruitPostRequest;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.controller.response.*;
import com.wccg.well_c_code_git_backend.domain.recruitpost.dto.service.response.*;
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
import org.springframework.web.bind.annotation.*;

import static com.wccg.well_c_code_git_backend.domain.recruitpost.mapper.RecruitPostDtoMapper.*;

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

    @GetMapping("")
    @Operation(
            summary = "모집 글 조회",
            description = """
                            팀원을 모집하는 모집 글을 조회할 수 있습니다.
                            """
    )
    public ResponseEntity<ApiResponse<ReadRecruitPostResponse>> readRecruitPost(@RequestParam Long recruitPostId){

        ServiceReadRecruitPostResponse serviceResponse = recruitPostService.readRecruitPost(recruitPostId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toReadRecruitPostResponse(serviceResponse),"모집 글 조회 완료"));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("")
    @Operation(
            summary = "모집 글 수정",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            - 모집 게시판에 작성된 모집 글을 수정합니다.
                            - 본인이 작성한 글만 수정할 수 있습니다.
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<UpdateRecruitPostResponse>> updateRecruitPost(@AuthenticationPrincipal User user, @RequestBody UpdateRecruitPostRequest request){
        ServiceUpdateRecruitPostResponse serviceResponse = recruitPostService.updateRecruitPost(user,toServiceUpdateRecruitPostRequest(request));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toUpdateRecruitPostResponse(serviceResponse),"모집 글 수정 완료"));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}")
    @Operation(
            summary = "모집 글 삭제",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            - 모집 게시판에 작성된 모집 글을 삭제합니다.
                            - 본인이 작성한 글만 삭제할 수 있습니다.
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<DeleteRecruitPostResponse>> deleteRecruitPost(@AuthenticationPrincipal User user, @PathVariable Long id){
        ServiceDeleteRecruitPostResponse serviceResponse = recruitPostService.deleteRecruitPost(user,id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toDeleteRecruitPostResponse(serviceResponse),"모집 글 삭제 완료"));
    }

    @GetMapping("/list")
    @Operation(
            summary = "모집 글 목록 조회",
            description = """
                            - 모집 게시판에서 글 목록을 조회합니다.
                            - 최신순으로 정렬되어있으며, 페이지 단위로 조회 가능합니다.
                            """
    )
    public ResponseEntity<ApiResponse<ReadRecruitPostListResponse>> readRecruitPostList(@RequestParam int page){
        ServiceReadRecruitPostListResponse serviceResponse = recruitPostService.readRecruitPostList(page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toReadRecruitPostListResponse(serviceResponse),"모집 글 목록 조회 완료"));
    }
}
