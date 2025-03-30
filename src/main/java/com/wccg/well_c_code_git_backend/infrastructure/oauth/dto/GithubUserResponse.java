package com.wccg.well_c_code_git_backend.infrastructure.oauth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
public class GithubUserResponse {

    private final String login;
    private final Long id;

    @JsonProperty("node_id")
    private final String nodeId;

    @JsonProperty("avatar_url")
    private final String avatarUrl;

    @JsonProperty("gravatar_id")
    private final String gravatarId;

    private final String url;

    @JsonProperty("html_url")
    private final String htmlUrl;

    @JsonProperty("followers_url")
    private final String followersUrl;

    @JsonProperty("following_url")
    private final String followingUrl;

    @JsonProperty("gists_url")
    private final String gistsUrl;

    @JsonProperty("starred_url")
    private final String starredUrl;

    @JsonProperty("subscriptions_url")
    private final String subscriptionsUrl;

    @JsonProperty("organizations_url")
    private final String organizationsUrl;

    @JsonProperty("repos_url")
    private final String reposUrl;

    @JsonProperty("events_url")
    private final String eventsUrl;

    @JsonProperty("received_events_url")
    private final String receivedEventsUrl;

    private final String type;

    @JsonProperty("site_admin")
    private final boolean siteAdmin;

    private final String name;
    private final String company;
    private final String blog;
    private final String location;
    private final String email;
    private final String bio;

    @JsonProperty("twitter_username")
    private final String twitterUsername;

    @JsonProperty("public_repos")
    private final int publicRepos;

    @JsonProperty("public_gists")
    private final int publicGists;

    private final int followers;
    private final int following;

    @JsonProperty("created_at")
    private final String createdAt;

    @JsonProperty("updated_at")
    private final String updatedAt;
}
