package com.wccg.well_c_code_git_backend.domain.team.repository;

import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    Optional<Team> findByIdAndIsActiveTrue(Long teamId);
}
