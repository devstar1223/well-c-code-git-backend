package com.wccg.well_c_code_git_backend.domain.accesstoken.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.repository.AccessTokenRepository;
import com.wccg.well_c_code_git_backend.domain.accesstoken.dto.AccessTokenSaveRequest;
import com.wccg.well_c_code_git_backend.domain.accesstoken.model.AccessToken;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.AccessTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public void save(AccessTokenSaveRequest accessTokenSaveRequest) {
        AccessToken accessToken = AccessToken.of(
                accessTokenSaveRequest.getAccessToken(),
                accessTokenSaveRequest.isActive()
        );
        accessTokenSaveRequest.getUser().addAccessToken(accessToken);
        accessTokenRepository.save(accessToken);
    }

    public void deactivatePreviousTokens(User user) {
        List<AccessToken> activeTokens = accessTokenRepository.findAllByUserAndIsActiveTrue(user);

        for (AccessToken token : activeTokens) {
            token.deactivate();
        }
    }

    public String getActiveAccessTokenByUserId(User user) {
        AccessToken accessToken = accessTokenRepository
                .findByUserAndIsActiveTrue(user)
                .orElseThrow(AccessTokenNotFoundException::new);
        return accessToken.getAccessToken();
    }
}
