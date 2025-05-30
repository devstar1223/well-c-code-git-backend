package com.wccg.well_c_code_git_backend.domain.recruitpost.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "id, is_active")
})
public class RecruitPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 5000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_post_status", nullable = false)
    private RecruitPostStatus recruitPostStatus;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public RecruitPost(String title, String content, RecruitPostStatus recruitPostStatus, boolean isActive) {
        this.title = title;
        this.content = content;
        this.recruitPostStatus = recruitPostStatus;
        this.isActive = isActive;
    }

    public static RecruitPost of(String title, String content, RecruitPostStatus recruitPostStatus, boolean isActive) {
        return RecruitPost.builder()
                .title(title)
                .content(content)
                .recruitPostStatus(recruitPostStatus)
                .isActive(isActive)
                .build();
    }
}
