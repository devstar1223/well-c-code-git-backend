package com.wccg.well_c_code_git_backend.domain.branch.repository;

import com.wccg.well_c_code_git_backend.domain.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}
