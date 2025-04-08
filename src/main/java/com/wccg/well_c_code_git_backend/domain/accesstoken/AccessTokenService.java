package com.wccg.well_c_code_git_backend.domain.accesstoken;

import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.AccessTokenNotFoundException;
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
        accessToken.setUser(accessTokenSaveRequest.getUser());
        accessTokenRepository.save(accessToken);
    }

    public void deactivatePreviousTokens(User user){
        List<AccessToken> activeTokens = accessTokenRepository.findAllByUserAndIsActiveTrue(user);

        for(AccessToken token : activeTokens){
            token.deactivate();
        }
    }

    public String getActiveAccessTokenByUserId(Long userId){
        AccessToken accessToken = accessTokenRepository
                .findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(AccessTokenNotFoundException::new);
        return accessToken.getAccessToken();
    }
}
