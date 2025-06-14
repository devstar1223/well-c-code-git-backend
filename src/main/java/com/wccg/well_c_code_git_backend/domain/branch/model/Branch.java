package com.wccg.well_c_code_git_backend.domain.branch.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.commit.model.Commit;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "wccg_repository_id, is_active")
})
public class Branch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 1024)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Commit> commits = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wccg_repository_id")
    private WccgRepository wccgRepository;

    @Builder
    private Branch(String name, boolean isActive){
        this.name = name;
        this.isActive = isActive;
    }

    public static Branch of(String name, boolean isActive){
        return Branch.builder()
                .name(name)
                .isActive(isActive)
                .build();
    }

    public void addCommit(Commit commit){
        commits.add(commit);
        commit.setBranch(this);
    }
}
