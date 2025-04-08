package com.wccg.well_c_code_git_backend.domain.wccgrepository;

import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.user.UserService;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.WccgRepositoryServiceSyncResponse;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.WccgRepositorySyncRequest;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.dto.WccgRepositorySyncResponse;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.UserMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wccgrepository")
public class WccgRepositoryController {

    private final UserService userService;
    private final WccgRepositoryService wccgRepositoryService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sync")
    public ResponseEntity<WccgRepositorySyncResponse> sync(@AuthenticationPrincipal User user,
                                                           @RequestBody WccgRepositorySyncRequest request) {
        if (!Objects.equals(user.getId(), request.getUserId())) {
            throw new UserMismatchException();
        }

        WccgRepositoryServiceSyncResponse ServiceResponse = wccgRepositoryService.syncRepositoryFrom(request.getUserId(),user);

        WccgRepositorySyncResponse ControllerResponse = new WccgRepositorySyncResponse(ServiceResponse.getRepositoryCount());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ControllerResponse);
    }
}
