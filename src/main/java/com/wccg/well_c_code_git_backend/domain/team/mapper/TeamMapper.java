package com.wccg.well_c_code_git_backend.domain.team.mapper;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.CreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceCreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;

public final class TeamMapper {
    private TeamMapper(){

    }

    public static ServiceCreateTeamRequest toServiceCreateTeamRequest(CreateTeamRequest request){
        return new ServiceCreateTeamRequest(
                request.getName(),
                request.getIntroduce(),
                request.getInfoImageUrl()
        );
    }

    public static ServiceCreateTeamResponse toServiceCreateTeamResponse(Team team){
        return new ServiceCreateTeamResponse(
                team.getName(),
                team.getIntroduce(),
                team.getInfoImageUrl()
        );
    }

    public static CreateTeamResponse toCreateTeamResponse(ServiceCreateTeamResponse serviceResponse){
        return new CreateTeamResponse(
                serviceResponse.getName(),
                serviceResponse.getIntroduce(),
                serviceResponse.getInfoImageUrl()
        );
    }
}
