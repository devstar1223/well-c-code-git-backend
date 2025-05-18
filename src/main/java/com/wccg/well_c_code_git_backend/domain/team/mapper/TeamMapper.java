package com.wccg.well_c_code_git_backend.domain.team.mapper;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.JoinTeamRequestRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.CreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.JoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.ReadJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.response.ReadTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceCreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceJoinTeamRequestRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceReadTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceReadJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.team.model.TeamUsers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static ServiceJoinTeamRequestRequest toServiceJoinTeamRequestRequest(JoinTeamRequestRequest request){
        return new ServiceJoinTeamRequestRequest(
                request.getTeamId(),
                request.getJoinIntroduce()
        );
    }

    public static ServiceJoinTeamRequestResponse toServiceJoinTeamRequestResponse(TeamUsers teamUsers){
        return new ServiceJoinTeamRequestResponse(
                teamUsers.getTeam().getName(),
                teamUsers.getJoinStatus().getDescription()
        );
    }

    public static JoinTeamRequestResponse toJoinTeamRequestResponse(ServiceJoinTeamRequestResponse serviceResponse){
        return new JoinTeamRequestResponse(
                serviceResponse.getTeamName(),
                serviceResponse.getJoinStatusDescription()
        );
    }

    public static ServiceReadJoinTeamRequestResponse toServiceReadJoinTeamRequestResponse(TeamUsers teamUsers){
        return new ServiceReadJoinTeamRequestResponse(
                teamUsers.getUser().getId(),
                teamUsers.getUser().getNickname(),
                teamUsers.getJoinIntroduce(),
                teamUsers.getUser().getProfileImageUrl(),
                teamUsers.getJoinStatus().getDescription()
        );
    }

    public static List<ReadJoinTeamRequestResponse> toReadJoinTeamRequestResponseList(List<ServiceReadJoinTeamRequestResponse> serviceResponselist) {
        return serviceResponselist.stream()
                .map(serviceResponse -> new ReadJoinTeamRequestResponse(
                        serviceResponse.getUserId(),
                        serviceResponse.getNickname(),
                        serviceResponse.getJoinIntroduce(),
                        serviceResponse.getProfileImageUrl(),
                        serviceResponse.getJoinStatusDescription()
                ))
                .toList();
    }

    public static ServiceReadTeamResponse toServiceReadTeamResponse(Team team, int teamMemberCount){
        return new ServiceReadTeamResponse(
                team.getName(),
                team.getIntroduce(),
                team.getInfoImageUrl(),
                teamMemberCount,
                team.getCreatedAt()
        );
    }

    public static ReadTeamResponse toReadTeamResponse(ServiceReadTeamResponse serviceReadTeamResponse){
        return new ReadTeamResponse(
                serviceReadTeamResponse.getName(),
                serviceReadTeamResponse.getIntroduce(),
                serviceReadTeamResponse.getInfoImageURL(),
                serviceReadTeamResponse.getTeamMemberCount(),
                serviceReadTeamResponse.getCreatedAt()
        );
    }
}
