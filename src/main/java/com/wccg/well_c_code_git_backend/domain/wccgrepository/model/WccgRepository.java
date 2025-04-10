package com.wccg.well_c_code_git_backend.domain.wccgrepository.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wccg_repository",
        indexes = {
                @Index(name = "idx_active_star", columnList = "is_active, star"),
                @Index(name = "idx_active_updated_at", columnList = "is_active, github_updated_at"),
                @Index(name = "idx_active_created_at", columnList = "is_active, github_created_at")
        })
public class WccgRepository extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "owner", nullable = false, length = 39)
    private String owner;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "github_created_at", nullable = false)
    private LocalDateTime githubCreatedAt;

    @Column(name = "github_updated_at", nullable = false)
    private LocalDateTime githubUpdatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_forked", nullable = false)
    private boolean isForked;

    @Column(name = "star")
    private int star;

    @Column(name = "language", length = 50)
    private String language;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //TODO : commit 테이블 연관관계 설정

    @Builder
    private WccgRepository(String name, String owner, String description, LocalDateTime githubCreatedAt, LocalDateTime githubUpdatedAt, boolean isForked, int star, String language, boolean isActive) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.githubCreatedAt = githubCreatedAt;
        this.githubUpdatedAt = githubUpdatedAt;
        this.isForked = isForked;
        this.star = star;
        this.language = language;
        this.isActive = isActive;
    }

    public static WccgRepository of(String name, String owner, String description, LocalDateTime githubCreatedAt, LocalDateTime githubUpdatedAt, boolean isForked, int star, String language, boolean isActive) {
        return WccgRepository.builder()
                .name(name)
                .owner(owner)
                .description(description)
                .githubCreatedAt(githubCreatedAt)
                .githubUpdatedAt(githubUpdatedAt)
                .isActive(isActive)
                .isForked(isForked)
                .star(star)
                .language(language)
                .build();
    }
}
