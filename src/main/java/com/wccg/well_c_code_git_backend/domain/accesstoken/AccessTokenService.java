package com.wccg.well_c_code_git_backend.domain.accesstoken;

import com.wccg.well_c_code_git_backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public void saveWithUser(String accessToken, User user){
        AccessToken token = AccessToken.of(accessToken, true);
        token.setUser(user);
        accessTokenRepository.save(token);
    }
}
