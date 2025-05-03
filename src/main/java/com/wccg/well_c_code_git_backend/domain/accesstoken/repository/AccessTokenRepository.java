package com.wccg.well_c_code_git_backend.domain.accesstoken.repository;

import com.wccg.well_c_code_git_backend.domain.accesstoken.model.AccessToken;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    List<AccessToken> findAllByUserAndIsActiveTrue(User user);

    Optional<AccessToken> findByUserIdAndIsActiveTrue(Long id);
}