package com.wccg.well_c_code_git_backend.domain.user.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.accesstoken.model.AccessToken;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
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

    @Column(name = "github_id", nullable = false, unique = true)
    private Long githubId;

    @Column(name = "github_login_id", nullable = false, length = 39)
    private String githubLoginId;

    @Column(name = "name", nullable = false, length = 39)
    private String name;

    @Column(name = "introduce",length = 200)
    private String introduce;

    @Column(name = "profile_image_url", nullable = false, length = 200)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 20)
    private UserRole userRole;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<AccessToken> accessTokens = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WccgRepository> wccgRepositories = new ArrayList<>();

    @Builder
    private User(Long githubId, String githubLoginId, String name, String introduce, String profileImageUrl, UserRole userRole, boolean isActive) {
        this.githubId = githubId;
        this.githubLoginId = githubLoginId;
        this.name = name;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
        this.userRole = userRole;
        this.isActive = isActive;
    }

    public static User of(Long githubId, String githubLoginId, String name, String introduce, String profileImageUrl, UserRole userRole, boolean isActive){
        return User.builder()
                .githubId(githubId)
                .githubLoginId(githubLoginId)
                .name(name)
                .introduce(introduce)
                .profileImageUrl(profileImageUrl)
                .userRole(userRole)
                .isActive(isActive)
                .build();
    }

    public void addAccessToken(AccessToken accessToken) {
        accessTokens.add(accessToken);
        accessToken.setUser(this);
    }

    public void addRepository(WccgRepository repository) {
        wccgRepositories.add(repository);
        repository.setUser(this);
    }
}
