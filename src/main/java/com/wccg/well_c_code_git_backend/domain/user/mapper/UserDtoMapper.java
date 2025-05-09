package com.wccg.well_c_code_git_backend.domain.user.mapper;

import com.wccg.well_c_code_git_backend.domain.user.dto.controller.response.NicknameAvaliableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.ServiceNicknameAvailableCheckRequest;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceNicknameAvailableCheckResponse;

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
}
