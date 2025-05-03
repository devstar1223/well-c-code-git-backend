package com.wccg.well_c_code_git_backend.domain.commit.repository;

import com.wccg.well_c_code_git_backend.domain.commit.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Long> {
    List<Commit> findAllByUserIdAndIsActiveTrue(Long userId);
}
