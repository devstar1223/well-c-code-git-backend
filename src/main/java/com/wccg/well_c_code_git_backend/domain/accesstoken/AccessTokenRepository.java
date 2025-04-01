package com.wccg.well_c_code_git_backend.domain.accesstoken;

import com.wccg.well_c_code_git_backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    List<AccessToken> findAllByUserAndIsActiveTrue(User user);
}