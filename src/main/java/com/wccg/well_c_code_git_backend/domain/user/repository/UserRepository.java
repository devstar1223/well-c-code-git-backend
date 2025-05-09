package com.wccg.well_c_code_git_backend.domain.user.repository;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGithubId(Long githubId);

    Optional<User> findByNickname(String nickname);
}
