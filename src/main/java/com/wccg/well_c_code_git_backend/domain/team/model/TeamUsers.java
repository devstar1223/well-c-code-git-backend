package com.wccg.well_c_code_git_backend.domain.team.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "team_id, is_active"),
        @Index(columnList = "team_id, is_active, created_at")
})
public class TeamUsers extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_status", nullable = false)
    private JoinStatus joinStatus;

    @Column(name = "join_introduce", nullable = false, length = 200)
    private String joinIntroduce;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Builder
    private TeamUsers(JoinStatus joinStatus, String joinIntroduce, boolean isActive) {
        this.joinStatus = joinStatus;
        this.joinIntroduce = joinIntroduce;
        this.isActive = isActive;
    }

    public static TeamUsers of(JoinStatus joinStatus, String joinIntroduce, boolean isActive){
        return TeamUsers.builder()
                .joinStatus(joinStatus)
                .joinIntroduce(joinIntroduce)
                .isActive(isActive)
                .build();
    }
}
