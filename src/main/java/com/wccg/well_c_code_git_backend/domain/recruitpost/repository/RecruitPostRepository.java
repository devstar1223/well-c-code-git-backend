package com.wccg.well_c_code_git_backend.domain.recruitpost.repository;

import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {
    Optional<RecruitPost> findByIdAndIsActiveTrue(Long recruitPostId);

    Page<RecruitPost> findAllByIsActiveTrue(Pageable pageable);
}
