package com.wccg.well_c_code_git_backend.domain.team.controller;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.JoinTeamRequestRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.RespondJoinTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.UpdateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.*;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceReadTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceReadJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceUpdateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;
    private final TeamRepository teamRepository;

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

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/join-request")
    @Operation(
            summary = "팀 가입 요청 수락/거절",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            - 현재 로그인한 팀의 관리자가 팀 가입 요청을 수락 또는 거절합니다.
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<Void>> respondJoinTeamRequest(@AuthenticationPrincipal User user, @RequestBody RespondJoinTeamRequest request){

        teamService.respondJoinTeamRequest(user,request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "팀 가입 요청 수락/거절 완료"));
    }

    @GetMapping("")
    @Operation(
            summary = "팀 조회(간단)",
            description = """
                            - 팀의 정보를 조회합니다. (스탯 제외 간단 버전)
                              - 팀 이름
                              - 팀 소개
                              - 팀 정보 사진
                              - 팀 인원 수
                              - 팀 생성일
                            """
    )
    public ResponseEntity<ApiResponse<ReadTeamResponse>> readTeam(@RequestParam Long teamId){

        ServiceReadTeamResponse serviceResponse = teamService.readTeam(teamId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toReadTeamResponse(serviceResponse),"팀 조회 완료"));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("")
    @Operation(
            summary = "팀 수정",
            description = """
                            **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                            - 현재 로그인한 팀의 관리자가 팀의 정보를 수정합니다.
                              - 팀 소개
                              - 팀 정보 사진
                            - 팀 이름은 변경 불가
                            """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<UpdateTeamResponse>> updateTeam(@AuthenticationPrincipal User user, @RequestBody UpdateTeamRequest request){
        ServiceUpdateTeamResponse serviceResponse = teamService.updateTeam(user, toServiceUpdateTeamRequest(request));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toUpdateTeamResponse(serviceResponse),"팀 수정 완료"));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/info-photo")
    @Operation(
            summary = "팀 정보 사진 파일 업로드",
            description = """
                    **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                    
                    팀의 관리자가 팀 정보 사진을 업로드하고, 업로드 가능한 파일일 경우 파일 URL을 반환 받습니다.
                    다음의 경우 파일의 업로드가 불가능 합니다.
                    - 확장자가 jpg, png, webp가 아닌 파일
                    - 파일의 크기가 15MB 이상인 파일
                    - 크기가 100*100 이 아닌 파일
                    """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<String>> infoPhotoUpload(@RequestParam("file") MultipartFile profilePhotoFile){

        String fileURL = teamService.infoPhotoUpload(profilePhotoFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(fileURL,"팀 정보 사진 파일 업로드 완료"));
    }
}
