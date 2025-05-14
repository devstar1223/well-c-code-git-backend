package com.wccg.well_c_code_git_backend.domain.team.model;

import com.wccg.well_c_code_git_backend.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
//        @Index(columnList = "name, is_active")
})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 12)
    private String name;

    @Column(name = "introduce", nullable = false, length = 200)
    private String introduce;

    @Column(name = "info_image_url", nullable = false, length = 200)
    private String infoImageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TeamUsers> teamUsers = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;

    public void addTeamUser(TeamUsers teamUser){
        teamUsers.add(teamUser);
        teamUser.setTeam(this);
    }

    @Builder
    private Team(String name, String introduce, String infoImageUrl, boolean isActive) {
        this.name = name;
        this.introduce = introduce;
        this.infoImageUrl = infoImageUrl;
        this.isActive = isActive;
    }

    public static Team of(String name, String introduce, String infoImageUrl, boolean isActive){
        return Team.builder()
                .name(name)
                .introduce(introduce)
                .infoImageUrl(infoImageUrl)
                .isActive(isActive)
                .build();
    }
}
