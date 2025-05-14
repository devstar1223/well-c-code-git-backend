package com.wccg.well_c_code_git_backend.domain.team.service;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.model.JoinStatus;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.team.model.TeamUsers;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamRepository;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamUsersRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.TeamNameConflictException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.TeamNameLengthInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper.toServiceCreateTeamResponse;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUsersRepository teamUsersRepository;

    public ServiceCreateTeamResponse createTeam(User user, CreateTeamRequest request) {
        String teamName = request.getName();
        teamNameValidate(teamName);

        Team newTeam = buildTeamFromRequest(request,user);
        teamRepository.save(newTeam);

        createTeamUsersRelationFromTeamLeader(newTeam);

        return toServiceCreateTeamResponse(newTeam);
    }

    private void createTeamUsersRelationFromTeamLeader(Team newTeam) {
        TeamUsers teamUsers = buildTeamUsersFromTeamLeader(newTeam);
        teamUsersRepository.save(teamUsers);
    }

    private TeamUsers buildTeamUsersFromTeamLeader(Team newTeam) {
        TeamUsers teamUsers = TeamUsers.of(
                JoinStatus.ACCEPTED,
                "이 팀을 최초로 생성했어요.",
                true
        );
        teamUsers.setTeam(newTeam);
        teamUsers.setUser(newTeam.getLeader());
        return teamUsers;
    }

    private Team buildTeamFromRequest(CreateTeamRequest request,User user) {
        Team team = Team.of(
                request.getName(),
                request.getIntroduce(),
                request.getInfoImageUrl(),
                true
        );
        team.setLeader(user);
        return team;
    }

    private void teamNameValidate(String teamName) {
        teamNameLengthValidate(teamName);
        teamNameConflictValidate(teamName);
    }

    private void teamNameLengthValidate(String teamName) {
        if(teamName.length() < 2 || teamName.length() > 12){
            throw new TeamNameLengthInvalidException();
        }
    }

    private void teamNameConflictValidate(String teamName) {
        if (teamRepository.findByName(teamName).isPresent()) {
            throw new TeamNameConflictException();
        }
    }


}
