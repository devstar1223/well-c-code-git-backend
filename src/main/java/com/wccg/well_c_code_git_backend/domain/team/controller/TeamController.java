package com.wccg.well_c_code_git_backend.domain.team.controller;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.JoinTeamRequestRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.CreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.JoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.ReadJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceReadJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.service.TeamService;
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

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    @Operation(
            summary = "팀 생성",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            
                            팀을 생성합니다.
                            - 팀 이름 (중복 불가 / 2자 - 12자)
                            - 팀 소개 문구
                            - 팀 프로필 사진
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<CreateTeamResponse>> createTeam(@AuthenticationPrincipal User user, @RequestBody CreateTeamRequest request){

        ServiceCreateTeamResponse serviceResponse = teamService.createTeam(user,request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toCreateTeamResponse(serviceResponse),"팀 생성 완료"));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/join-request")
    @Operation(
            summary = "팀 가입 요청",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            
                            API 설명
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<JoinTeamRequestResponse>> joinTeamRequest(@AuthenticationPrincipal User user, @RequestBody JoinTeamRequestRequest request){

        ServiceJoinTeamRequestResponse serviceResponse = teamService.joinTeamRequest(user,toServiceJoinTeamRequestRequest(request));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toJoinTeamRequestResponse(serviceResponse),"팀 가입 요청 완료"));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/join-request")
    @Operation(
            summary = "팀 가입 요청 목록 조회",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            - 현재 로그인한 관리자가 속한 팀의 가입 요청 목록을 조회합니다.
                            - 가입 요청은 최신순(요청일 기준 내림차순)으로 정렬되어 반환됩니다.
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<List<ReadJoinTeamRequestResponse>>> readJoinTeamRequest(@AuthenticationPrincipal User user, @RequestParam Long teamId) {
        List<ServiceReadJoinTeamRequestResponse> serviceResponseList = teamService.readJoinTeamRequest(user, teamId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toReadJoinTeamRequestResponseList(serviceResponseList), "가입 요청 목록 조회 완료"));
    }

}
