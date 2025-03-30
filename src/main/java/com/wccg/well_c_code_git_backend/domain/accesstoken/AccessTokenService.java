package com.wccg.well_c_code_git_backend.domain.accesstoken;

import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.AccessTokenSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public void save(AccessTokenSaveRequest accessTokenSaveRequest) {
        AccessToken accessToken = AccessToken.of(
                accessTokenSaveRequest.getAccessToken(),
                accessTokenSaveRequest.isActive()
        );
        accessToken.setUser(accessTokenSaveRequest.getUser());
        accessTokenRepository.save(accessToken);
    }
}
