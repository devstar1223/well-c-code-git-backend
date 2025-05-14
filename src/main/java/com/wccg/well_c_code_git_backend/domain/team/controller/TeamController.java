package com.wccg.well_c_code_git_backend.domain.team.controller;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.CreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper.toCreateTeamResponse;

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
    public ResponseEntity<ApiResponse<CreateTeamResponse>> createTeam(@AuthenticationPrincipal User user, CreateTeamRequest request){

        ServiceCreateTeamResponse serviceResponse = teamService.createTeam(user,request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toCreateTeamResponse(serviceResponse),"팀 생성 완료"));
    }
}
