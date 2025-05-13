package com.wccg.well_c_code_git_backend.domain.user.mapper;

import com.wccg.well_c_code_git_backend.domain.user.dto.controller.request.UpdateProfileRequest;
import com.wccg.well_c_code_git_backend.domain.user.dto.controller.response.NicknameAvaliableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.controller.response.ReadProfileResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.controller.response.UpdateProfileResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.ServiceNicknameAvailableCheckRequest;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.ServiceUpdateProfileRequest;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceNicknameAvailableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceReadProfileResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceUpdateProfileResponse;
import com.wccg.well_c_code_git_backend.domain.user.model.User;

public final class UserDtoMapper {

    private UserDtoMapper(){

    }

    public static NicknameAvaliableCheckResponse toNicknameAvaliableCheckResponse(ServiceNicknameAvailableCheckResponse serviceResponse){
        return new NicknameAvaliableCheckResponse(
                serviceResponse.isAvaliable(),
                serviceResponse.getNickname()
        );
    }

    public static ServiceNicknameAvailableCheckRequest toServiceNicknameAvailableCheckRequest(String nickname){
        return new ServiceNicknameAvailableCheckRequest(nickname);
    }

    public static ServiceNicknameAvailableCheckResponse toServiceNicknameAvailableCheckResponse(boolean available,String nickname){
        return new ServiceNicknameAvailableCheckResponse(available,nickname);
    }

    public static ServiceUpdateProfileRequest toServiceUpdateProfileRequest(UpdateProfileRequest request){
        return new ServiceUpdateProfileRequest(
                request.getNickname(),
                request.getIntroduce(),
                request.getProfileImageUrl()
        );
    }

    public static ServiceUpdateProfileResponse toServiceUpdateProfileResponse(User user){
        return new ServiceUpdateProfileResponse(
                user.getNickname(),
                user.getIntroduce(),
                user.getProfileImageUrl()
        );
    }

    public static UpdateProfileResponse toUpdateProfileResponse(ServiceUpdateProfileResponse serviceResponse){
        return new UpdateProfileResponse(
                serviceResponse.getNickname(),
                serviceResponse.getIntroduce(),
                serviceResponse.getProfileImageUrl()
        );
    }

    public static ServiceReadProfileResponse toServiceReadProfileResponse(User user){
        return new ServiceReadProfileResponse(
                user.getGithubLoginId(),
                user.getNickname(),
                user.getIntroduce(),
                user.getProfileImageUrl()
        );
    }

    public static ReadProfileResponse toReadProfileResponse(ServiceReadProfileResponse serviceResponse){
        return new ReadProfileResponse(
                serviceResponse.getGithubLoginId(),
                serviceResponse.getNickname(),
                serviceResponse.getIntroduce(),
                serviceResponse.getProfileImageUrl()
        );
    }
}
