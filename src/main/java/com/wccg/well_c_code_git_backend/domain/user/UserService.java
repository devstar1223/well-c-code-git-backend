package com.wccg.well_c_code_git_backend.domain.user;

import com.wccg.well_c_code_git_backend.infrastructure.oauth.dto.UserSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserSaveRequest request) {
        User user = User.of(
                request.getGithubId(),
                request.getGithubLoginId(),
                request.getName(),
                request.getIntroduce(),
                request.getProfileImageUrl(),
                request.isActive()
        );

        return userRepository.save(user);
    }
}
