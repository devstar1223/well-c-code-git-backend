package com.wccg.well_c_code_git_backend.domain.user.controller;

import com.wccg.well_c_code_git_backend.domain.user.dto.controller.response.NicknameAvaliableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceNicknameAvailableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.domain.user.service.UserProfileService;
import com.wccg.well_c_code_git_backend.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.wccg.well_c_code_git_backend.domain.user.mapper.UserDtoMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;

    @GetMapping("/nickname/available")
    @Operation(
            summary = "닉네임 사용 가능 여부 확인",
            description = """
                    입력한 닉네임이 사용 가능한지 체크합니다.
                    """
    )
    public ResponseEntity<ApiResponse<NicknameAvaliableCheckResponse>> nicknameAvailableCheck(@RequestParam String nickname) {

        ServiceNicknameAvailableCheckResponse serviceResponse = userProfileService.nicknameAvailableCheck(toServiceNicknameAvailableCheckRequest(nickname));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toNicknameAvaliableCheckResponse(serviceResponse),"사용 가능한 닉네임 입니다."));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/profile-photo")
    @Operation(
            summary = "프로필 사진 파일 업로드",
            description = """
                    **⚠️ 본 API는 JWT 인증이 필요하며, 사용자 권한(`ROLE_USER`)이 요구됩니다.**
                    
                    프로필 사진을 업로드하고, 업로드 가능한 파일일 경우 파일 URL을 반환 받습니다.
                    다음의 경우 파일의 업로드가 불가능 합니다.
                    - 확장자가 jpg, png, webp가 아닌 파일
                    - 파일의 크기가 15MB 이상인 파일
                    - 크기가 100*100 이 아닌 파일
                    """,
            security = @SecurityRequirement(name = "JWT")
    )
    public ResponseEntity<ApiResponse<String>> profilePhotoUpload(@RequestParam("file") MultipartFile profilePhotoFile){

        String fileURL = userProfileService.profilePhotoUpload(profilePhotoFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(fileURL,"프로필 사진 파일 업로드 완료"));
    }
}
