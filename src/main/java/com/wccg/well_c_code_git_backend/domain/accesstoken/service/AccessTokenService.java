package com.wccg.well_c_code_git_backend.domain.accesstoken.service;

import com.wccg.well_c_code_git_backend.domain.accesstoken.repository.AccessTokenRepository;
import com.wccg.well_c_code_git_backend.domain.accesstoken.dto.AccessTokenSaveRequest;
import com.wccg.well_c_code_git_backend.domain.accesstoken.model.AccessToken;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        userAccessTokenAllDeactivate(activeTokens);
    }

    private static void userAccessTokenAllDeactivate(List<AccessToken> activeTokens) {
        for (AccessToken token : activeTokens) {
            token.deactivate();
        }
    }

    public Optional<AccessToken> getActiveAccessTokenByUserId(Long id) {
        return accessTokenRepository.findByUserIdAndIsActiveTrue(id);
    }
}
