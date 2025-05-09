package com.wccg.well_c_code_git_backend.domain.user.controller;

import com.wccg.well_c_code_git_backend.domain.commit.dto.controller.response.SyncCommitResponse;
import com.wccg.well_c_code_git_backend.domain.commit.dto.service.response.ServiceSyncCommitResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.controller.response.NicknameAvaliableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceNicknameAvailableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.service.UserService;
import com.wccg.well_c_code_git_backend.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.wccg.well_c_code_git_backend.domain.user.mapper.UserDtoMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/nickname/available")
    @Operation(
            summary = "닉네임 사용 가능 여부 확인",
            description = """
                    입력한 닉네임이 사용 가능한지 체크합니다.
                    """
    )
    public ResponseEntity<ApiResponse<NicknameAvaliableCheckResponse>> nicknameAvailableCheck(@RequestParam String nickname) {

        ServiceNicknameAvailableCheckResponse serviceResponse = userService.nicknameAvailableCheck(toServiceNicknameAvailableCheckRequest(nickname));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(toNicknameAvaliableCheckResponse(serviceResponse),"사용 가능한 닉네임 입니다."));
    }
}
