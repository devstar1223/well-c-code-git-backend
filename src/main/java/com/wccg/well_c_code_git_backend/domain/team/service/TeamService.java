package com.wccg.well_c_code_git_backend.domain.team.service;

import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.CreateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.controller.request.RespondJoinTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceJoinTeamRequestRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceReadTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.request.ServiceUpdateTeamRequest;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceCreateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceReadJoinTeamRequestResponse;
import com.wccg.well_c_code_git_backend.domain.team.dto.service.response.ServiceUpdateTeamResponse;
import com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper;
import com.wccg.well_c_code_git_backend.domain.team.model.JoinStatus;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.team.model.TeamUsers;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamRepository;
import com.wccg.well_c_code_git_backend.domain.team.repository.TeamUsersRepository;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.ImageTooLargeException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.InvalidImageDimensionsException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.InvalidImageExtensionException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.S3FileUploadFailedException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.team.*;
import com.wccg.well_c_code_git_backend.global.s3.S3Uploader;
import com.wccg.well_c_code_git_backend.global.s3.UploadFileType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static com.wccg.well_c_code_git_backend.domain.team.mapper.TeamMapper.*;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    private final TeamUsersRepository teamUsersRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ServiceCreateTeamResponse createTeam(User user, CreateTeamRequest request) {
        String teamName = request.getName();
        teamNameValidate(teamName);

        Team newTeam = buildTeamFromRequest(request, user);
        teamRepository.save(newTeam);

        createTeamUsersRelationFromTeamLeader(newTeam);

        return toServiceCreateTeamResponse(newTeam);
    }

    @Transactional
    public ServiceJoinTeamRequestResponse joinTeamRequest(User user, ServiceJoinTeamRequestRequest request) {

        TeamUsers newTeamUsers = buildTeamUsers(request);
        createTeamUsersRelationFromJoinRequest(user, request, newTeamUsers);
        teamUsersRepository.save(newTeamUsers);
        return toServiceJoinTeamRequestResponse(newTeamUsers);
    }

    public List<ServiceReadJoinTeamRequestResponse> readJoinTeamRequest(User user, Long teamId) {
        Team team = teamNotFoundValidate(teamId);
        teamLeaderValidate(user, team);
        List<TeamUsers> teamUsers = getActiveTeamUsers(teamId);
        return mapToServiceResponses(teamUsers);
    }

    @Transactional
    public void respondJoinTeamRequest(User user, RespondJoinTeamRequest request) {
        TeamUsers teamUsers = teamUsersNotFoundValidate(request);

        Team team = teamNotFoundValidate(teamUsers.getTeam().getId());

        teamLeaderValidate(user, team);

        TeamUsers newTeamUsers = teamUsers.approveJoinRequest(request.isAccepted());

        teamUsersRepository.save(newTeamUsers);
    }

    public ServiceReadTeamResponse readTeam(Long teamId) {
        int teamMemberCount = getTeamMemberCount(teamId);
        Team team = teamNotFoundValidate(teamId);
        return toServiceReadTeamResponse(team,teamMemberCount);
    }

    @Transactional
    public String infoPhotoUpload(MultipartFile profilePhotoFile) {

        imageExtensionValidate(profilePhotoFile);

        imageSizeValidate(profilePhotoFile);

        imageDimensionsValidate(profilePhotoFile);

        return s3Uploader.upload(profilePhotoFile, UploadFileType.TEAM_INFO_PHOTO);
    }

    @Transactional
    public ServiceUpdateTeamResponse updateTeam(User user, ServiceUpdateTeamRequest serviceRequest) {
        Team team = teamNotFoundValidate(serviceRequest.getTeamId());
        teamLeaderValidate(user, team);

        String introduce = serviceRequest.getIntroduce();
        introduceLengthValidate(introduce);
        String infoImageUrl = serviceRequest.getInfoImageUrl();

        team.updateTeamInfo(introduce,infoImageUrl);

        teamRepository.save(team);

        return toServiceUpdateTeamResponse(team);
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

    private TeamUsers teamUsersNotFoundValidate(RespondJoinTeamRequest request) {
        return teamUsersRepository.findById(request.getTeamUsersId())
                .orElseThrow(TeamJoinRequestNotFoundException::new);
    }

    private int getTeamMemberCount(Long teamId) {
        List<TeamUsers> teamUsersList = teamUsersRepository.findAllByTeamIdAndJoinStatusAndIsActiveTrue(teamId, JoinStatus.ACCEPTED);
        return teamUsersList.size();
    }

    private void introduceLengthValidate(String introduce) {
        if(introduce.length() > 200){
            throw new TeamIntroduceTooLongException();
        }
    }

    private void imageDimensionsValidate(MultipartFile profilePhotoFile) {
        try {
            BufferedImage image = ImageIO.read(profilePhotoFile.getInputStream());
            if (image.getWidth() != 100 || image.getHeight() != 100) {
                throw new InvalidImageDimensionsException();
            }
        } catch (IOException e) {
            throw new S3FileUploadFailedException();
        }
    }

    private void imageSizeValidate(MultipartFile profilePhotoFile) {
        if (profilePhotoFile.getSize() > 15 * 1024 * 1024) {
            throw new ImageTooLargeException();
        }
    }

    private void imageExtensionValidate(MultipartFile profilePhotoFile) {
        String originalFilename = profilePhotoFile.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches("(?i)^.+\\.(jpg|png|webp)$")) {
            throw new InvalidImageExtensionException();
        }
    }
}
