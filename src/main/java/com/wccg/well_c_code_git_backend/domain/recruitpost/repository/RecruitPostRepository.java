package com.wccg.well_c_code_git_backend.domain.recruitpost.repository;

import com.wccg.well_c_code_git_backend.domain.recruitpost.model.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {

}
