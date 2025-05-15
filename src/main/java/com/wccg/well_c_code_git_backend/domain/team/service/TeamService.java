package com.wccg.well_c_code_git_backend.domain.team.service;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.RespondJoinTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceJoinTeamRequestRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceReadJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper;
import com.wccg.well_c_code_git_backend.domain.team.model.JoinStatus;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.team.model.TeamUsers;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamRepository;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamUsersRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper.*;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUsersRepository teamUsersRepository;

    @Transactional
    public ServiceCreateTeamResponse createTeam(User user, CreateTeamRequest request) {
        String teamName = request.getName();
        teamNameValidate(teamName);

        Team newTeam = buildTeamFromRequest(request, user);
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

    private Team buildTeamFromRequest(CreateTeamRequest request, User user) {
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
        if (teamName.length() < 2 || teamName.length() > 12) {
            throw new TeamNameLengthInvalidException();
        }
    }

    private void teamNameConflictValidate(String teamName) {
        if (teamRepository.findByName(teamName).isPresent()) {
            throw new TeamNameConflictException();
        }
    }


    @Transactional
    public ServiceJoinTeamRequestResponse joinTeamRequest(User user, ServiceJoinTeamRequestRequest request) {

        TeamUsers newTeamUsers = buildTeamUsers(request);
        createTeamUsersRelationFromJoinRequest(user, request, newTeamUsers);
        teamUsersRepository.save(newTeamUsers);
        return toServiceJoinTeamRequestResponse(newTeamUsers);
    }

    private void createTeamUsersRelationFromJoinRequest(User user, ServiceJoinTeamRequestRequest request, TeamUsers newTeamUsers) {
        newTeamUsers.setUser(user);
        Long teamId = request.getTeamId();
        Team joinTeam = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
        newTeamUsers.setTeam(joinTeam);
    }

    private TeamUsers buildTeamUsers(ServiceJoinTeamRequestRequest request) {
        return TeamUsers.of(
                JoinStatus.PENDING,
                request.getJoinIntroduce(),
                true
        );
    }

    public List<ServiceReadJoinTeamRequestResponse> readJoinTeamRequest(User user, Long teamId) {
        Team team = teamNotFoundValidate(teamId);
        teamLeaderValidate(user, team);
        List<TeamUsers> teamUsers = getActiveTeamUsers(teamId);
        return mapToServiceResponses(teamUsers);
    }

    private void teamLeaderValidate(User user, Team team) {
        if (!user.getId().equals(team.getLeader().getId())) {
            throw new TeamApplicantForbiddenException();
        }
    }

    private Team teamNotFoundValidate(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
    }

    private List<TeamUsers> getActiveTeamUsers(Long teamId) {
        return teamUsersRepository.findAllByTeamIdAndIsActiveTrueOrderByCreatedAtDesc(teamId);
    }

    private List<ServiceReadJoinTeamRequestResponse> mapToServiceResponses(List<TeamUsers> teamUsersList) {
        return teamUsersList.stream()
                .map(TeamMapper::toServiceReadJoinTeamRequestResponse)
                .toList();
    }

    public void respondJoinTeamRequest(User user, RespondJoinTeamRequest request) {
        TeamUsers teamUsers = teamUsersNotFoundValidate(request);

        Team team = teamNotFoundValidate(teamUsers.getTeam().getId());

        teamLeaderValidate(user, team);

        TeamUsers newTeamUsers = teamUsers.approveJoinRequest(request.isAccepted());

        teamUsersRepository.save(newTeamUsers);
    }

    private TeamUsers teamUsersNotFoundValidate(RespondJoinTeamRequest request) {
        return teamUsersRepository.findById(request.getTeamUsersId())
                .orElseThrow(TeamJoinRequestNotFound::new);
    }
}
