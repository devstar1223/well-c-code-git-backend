package com.wccg.well_c_code_git_backend.domain.user.service;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.UserSaveRequest;
import com.wccg.well_c_code_git_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserSaveRequest request) {
        User user = User.of(
                request.getGithubId(),
                request.getGithubLoginId(),
                request.getName(),
                request.getNickname(),
                request.getIntroduce(),
                request.getProfileImageUrl(),
                request.getUserRole(),
                request.isActive()
        );

        return userRepository.save(user);
    }

    public Optional<User> getUserByGithubId(Long githubId) {
        return userRepository.findByGithubId(githubId);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
