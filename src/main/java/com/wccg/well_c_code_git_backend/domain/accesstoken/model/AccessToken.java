package com.wccg.well_c_code_git_backend.domain.accesstoken.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "user_id, is_active")
})
public class AccessToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_token", nullable = false, length = 255)
    private String accessToken;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private AccessToken(String accessToken, boolean isActive) {
        this.accessToken = accessToken;
        this.isActive = isActive;
    }

    public static AccessToken of(String accessToken, boolean isActive) {
        return AccessToken.builder()
                .accessToken(accessToken)
                .isActive(isActive)
                .build();
    }

    public void deactivate() {
        this.isActive = false;
    }
}
