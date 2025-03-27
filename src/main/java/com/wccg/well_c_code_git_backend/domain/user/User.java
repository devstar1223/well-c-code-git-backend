package com.wccg.well_c_code_git_backend.domain.user;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.accesstoken.AccessToken;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.WccgRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_id", nullable = false, length = 39)
    private String githubId;

    @Column(name = "name", nullable = false, length = 39)
    private String name;

    @Column(name = "introduce",length = 200)
    private String introduce;

    @Column(name = "profile_image_url", nullable = false, length = 200)
    private String profileImageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    // 연관관계: 유저는 여러 리포지토리, 토큰을 가질 수 있음
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccessToken> accessTokens = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WccgRepository> wccgRepositories = new ArrayList<>();

    @Builder
    private User(String githubId, String name, String introduce, String profileImageUrl, boolean isActive) {
        this.githubId = githubId;
        this.name = name;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
        this.isActive = isActive;
    }

    public static User of(String githubId, String name, String introduce, String profileImageUrl, boolean isActive){
        return User.builder()
                .githubId(githubId)
                .name(name)
                .introduce(introduce)
                .profileImageUrl(profileImageUrl)
                .isActive(isActive)
                .build();
    }
}
