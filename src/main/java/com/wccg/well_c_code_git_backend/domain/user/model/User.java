package com.wccg.well_c_code_git_backend.domain.user.model;

import com.wccg.well_c_code_git_backend.domain.BaseEntity;
import com.wccg.well_c_code_git_backend.domain.accesstoken.model.AccessToken;
import com.wccg.well_c_code_git_backend.domain.commit.model.Commit;
import com.wccg.well_c_code_git_backend.domain.team.model.Team;
import com.wccg.well_c_code_git_backend.domain.team.model.TeamUsers;
import com.wccg.well_c_code_git_backend.domain.wccgrepository.model.WccgRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(columnList = "nickname, is_active")
        }
)
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

    @Column(name = "nickname", nullable = false, length = 12, unique = true)
    private String nickname;

    @Column(name = "introduce",length = 200)
    private String introduce;

    @Column(name = "profile_image_url", nullable = false, length = 200)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 20)
    private UserRole userRole;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<AccessToken> accessTokens = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WccgRepository> wccgRepositories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Commit> commits = new ArrayList<>();

    @OneToMany(mappedBy = "leader", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TeamUsers> teamUsers = new ArrayList<>();

    @Builder
    private User(Long githubId, String githubLoginId, String name, String nickname, String introduce, String profileImageUrl, UserRole userRole, boolean isActive) {
        this.githubId = githubId;
        this.githubLoginId = githubLoginId;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
        this.userRole = userRole;
        this.isActive = isActive;
    }

    public static User of(Long githubId, String githubLoginId, String name, String nickname, String introduce, String profileImageUrl, UserRole userRole, boolean isActive){
        return User.builder()
                .githubId(githubId)
                .githubLoginId(githubLoginId)
                .name(name)
                .nickname(nickname)
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

    public void addCommit(Commit commit) {
        commits.add(commit);
        commit.setUser(this);
    }

    public void addTeam(Team team){
        teams.add(team);
        team.setLeader(this);
    }

    public void addTeamUser(TeamUsers teamUser){
        teamUsers.add(teamUser);
        teamUser.setUser(this);
    }

    public void updateProfile(String nickname, String introduce, String profileImageUrl) {
            this.nickname = nickname;
            this.introduce = introduce;
            this.profileImageUrl = profileImageUrl;
    }
}
