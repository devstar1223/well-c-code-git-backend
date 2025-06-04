package com.wccg.well_c_code_git_backend.domain.team.repository;

import com.wccg.well_c_code_git_backend.domain.team.model.JoinStatus;
import com.wccg.well_c_code_git_backend.domain.team.model.TeamUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamUsersRepository extends JpaRepository<TeamUsers,Long> {
    List<TeamUsers> findAllByTeamIdAndIsActiveTrueOrderByCreatedAtDesc(Long teamId);

    List<TeamUsers> findAllByTeamIdAndJoinStatusAndIsActiveTrue(Long teamId, JoinStatus joinStatus);
}
