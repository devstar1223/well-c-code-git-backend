package com.wccg.well_c_code_git_backend.domain.wccgrepository.repository;

import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WccgRepositoryRepository extends JpaRepository<WccgRepository, Long> {
    List<WccgRepository> findAllByUserIdAndIsActiveTrue(Long userId,Sort sort);

    List<WccgRepository> findAllByUserIdAndIsActiveTrue(Long userId);
}

