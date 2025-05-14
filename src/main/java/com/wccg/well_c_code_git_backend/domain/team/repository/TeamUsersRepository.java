package com.wccg.well_c_code_git_backend.domain.team.repository;

import com.wccg.well_c_code_git_backend.domain.team.model.TeamUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUsersRepository extends JpaRepository<TeamUsers,Long> {
}
