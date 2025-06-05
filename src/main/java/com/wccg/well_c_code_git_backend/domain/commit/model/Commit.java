package com.wccg.well_c_code_git_backend.domain.commit.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.branch.model.Branch;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "user_id, is_active")
})
public class Commit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false, length = 255)
    private String message;

    @Column(name = "hash_value", nullable = false, length = 40)
    private String hashValue;

    @Column(name = "commit_created_at", nullable = false)
    private LocalDateTime commitCreatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Commit(String message, String hashValue, LocalDateTime commitCreatedAt, boolean isActive) {
        this.message = message;
        this.hashValue = hashValue;
        this.commitCreatedAt = commitCreatedAt;
        this.isActive = isActive;
    }

    public static Commit of(String message, String hashValue, LocalDateTime commitCreatedAt, boolean isActive) {
        return Commit.builder()
                .message(message)
                .hashValue(hashValue)
                .commitCreatedAt(commitCreatedAt)
                .isActive(isActive)
                .build();
    }
}