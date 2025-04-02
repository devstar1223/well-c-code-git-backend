package com.wccg.well_c_code_git_backend.global.security.oauth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GithubUserResponse {

    private final String login;
    private final Long id;
    private final String nodeId;
    private final String avatarUrl;
    private final String gravatarId;
    private final String url;
    private final String htmlUrl;
    private final String followersUrl;
    private final String followingUrl;
    private final String gistsUrl;
    private final String starredUrl;
    private final String subscriptionsUrl;
    private final String organizationsUrl;
    private final String reposUrl;
    private final String eventsUrl;
    private final String receivedEventsUrl;
    private final String type;
    private final boolean siteAdmin;
    private final String name;
    private final String company;
    private final String blog;
    private final String location;
    private final String email;
    private final String bio;
    private final String twitterUsername;
    private final int publicRepos;
    private final int publicGists;
    private final int followers;
    private final int following;
    private final String createdAt;
    private final String updatedAt;

    @JsonCreator
    public GithubUserResponse(
            @JsonProperty("login") String login,
            @JsonProperty("id") Long id,
            @JsonProperty("node_id") String nodeId,
            @JsonProperty("avatar_url") String avatarUrl,
            @JsonProperty("gravatar_id") String gravatarId,
            @JsonProperty("url") String url,
            @JsonProperty("html_url") String htmlUrl,
            @JsonProperty("followers_url") String followersUrl,
            @JsonProperty("following_url") String followingUrl,
            @JsonProperty("gists_url") String gistsUrl,
            @JsonProperty("starred_url") String starredUrl,
            @JsonProperty("subscriptions_url") String subscriptionsUrl,
            @JsonProperty("organizations_url") String organizationsUrl,
            @JsonProperty("repos_url") String reposUrl,
            @JsonProperty("events_url") String eventsUrl,
            @JsonProperty("received_events_url") String receivedEventsUrl,
            @JsonProperty("type") String type,
            @JsonProperty("site_admin") boolean siteAdmin,
            @JsonProperty("name") String name,
            @JsonProperty("company") String company,
            @JsonProperty("blog") String blog,
            @JsonProperty("location") String location,
            @JsonProperty("email") String email,
            @JsonProperty("bio") String bio,
            @JsonProperty("twitter_username") String twitterUsername,
            @JsonProperty("public_repos") int publicRepos,
            @JsonProperty("public_gists") int publicGists,
            @JsonProperty("followers") int followers,
            @JsonProperty("following") int following,
            @JsonProperty("created_at") String createdAt,
            @JsonProperty("updated_at") String updatedAt
    ) {
        this.login = login;
        this.id = id;
        this.nodeId = nodeId;
        this.avatarUrl = avatarUrl;
        this.gravatarId = gravatarId;
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.followersUrl = followersUrl;
        this.followingUrl = followingUrl;
        this.gistsUrl = gistsUrl;
        this.starredUrl = starredUrl;
        this.subscriptionsUrl = subscriptionsUrl;
        this.organizationsUrl = organizationsUrl;
        this.reposUrl = reposUrl;
        this.eventsUrl = eventsUrl;
        this.receivedEventsUrl = receivedEventsUrl;
        this.type = type;
        this.siteAdmin = siteAdmin;
        this.name = name;
        this.company = company;
        this.blog = blog;
        this.location = location;
        this.email = email;
        this.bio = bio;
        this.twitterUsername = twitterUsername;
        this.publicRepos = publicRepos;
        this.publicGists = publicGists;
        this.followers = followers;
        this.following = following;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
