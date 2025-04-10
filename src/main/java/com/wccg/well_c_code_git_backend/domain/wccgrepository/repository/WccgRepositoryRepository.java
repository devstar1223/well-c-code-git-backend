package com.wccg.well_c_code_git_backend.domain.wccgrepository.repository;

import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WccgRepositoryRepository extends JpaRepository<WccgRepository, Long> {
    List<WccgRepository> findAllByIsActiveTrue(Sort sort);
}

