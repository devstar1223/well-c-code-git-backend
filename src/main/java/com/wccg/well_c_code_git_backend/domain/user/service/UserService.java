package com.wccg.well_c_code_git_backend.domain.user.service;

import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.ServiceNicknameAvailableCheckRequest;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceNicknameAvailableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.UserSaveRequest;
import com.wccg.well_c_code_git_backend.domain.user.repository.UserRepository;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.NicknameConflictException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.NicknameLengthInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.wccg.well_c_code_git_backend.domain.user.mapper.UserDtoMapper.toServiceNicknameAvailableCheckResponse;

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

    public Optional<User> getUserByGithubId(Long githubId){
        return userRepository.findByGithubId(githubId);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public ServiceNicknameAvailableCheckResponse nicknameAvailableCheck(ServiceNicknameAvailableCheckRequest request) {
        String nickname = request.getNickname();
        nicknameLengthValidate(nickname);
        nicknameConflictValidate(nickname);
        return toServiceNicknameAvailableCheckResponse(true,nickname);
    }

    private void nicknameConflictValidate(String nickname) {
        Optional<User> optionalUser = userRepository.findByNickname(nickname);
        if(optionalUser.isPresent()){
            throw new NicknameConflictException();
        }
    }

    private void nicknameLengthValidate(String nickname) {
        if(nickname.length() < 2 || nickname.length() > 12){
            throw new NicknameLengthInvalidException();
        }
    }
}
