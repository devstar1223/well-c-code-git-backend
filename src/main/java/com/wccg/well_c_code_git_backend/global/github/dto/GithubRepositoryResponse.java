package com.wccg.well_c_code_git_backend.global.github.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@ToString
public class GithubRepositoryResponse {

    private final Long id;
    private final String nodeId;
    private final String name;
    private final String fullName;
    private final boolean privateRepository;
    private final boolean fork;
    private final Owner owner;
    private final String htmlUrl;
    private final String description;
    private final String url;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    private final OffsetDateTime pushedAt;
    private final String homepage;
    private final int size;
    private final int stargazersCount;
    private final int watchersCount;
    private final String language;
    private final int forksCount;
    private final int openIssuesCount;
    private final License license;
    private final boolean allowForking;
    private final boolean isTemplate;
    private final List<String> topics;
    private final String visibility;
    private final String defaultBranch;

    @JsonCreator
    public GithubRepositoryResponse(
            @JsonProperty("id") Long id,
            @JsonProperty("node_id") String nodeId,
            @JsonProperty("name") String name,
            @JsonProperty("full_name") String fullName,
            @JsonProperty("private") boolean privateRepository,
            @JsonProperty("fork") boolean fork,
            @JsonProperty("owner") Owner owner,
            @JsonProperty("html_url") String htmlUrl,
            @JsonProperty("description") String description,
            @JsonProperty("url") String url,
            @JsonProperty("created_at") OffsetDateTime createdAt,
            @JsonProperty("updated_at") OffsetDateTime updatedAt,
            @JsonProperty("pushed_at") OffsetDateTime pushedAt,
            @JsonProperty("homepage") String homepage,
            @JsonProperty("size") int size,
            @JsonProperty("stargazers_count") int stargazersCount,
            @JsonProperty("watchers_count") int watchersCount,
            @JsonProperty("language") String language,
            @JsonProperty("forks_count") int forksCount,
            @JsonProperty("open_issues_count") int openIssuesCount,
            @JsonProperty("license") License license,
            @JsonProperty("allow_forking") boolean allowForking,
            @JsonProperty("is_template") boolean isTemplate,
            @JsonProperty("topics") List<String> topics,
            @JsonProperty("visibility") String visibility,
            @JsonProperty("default_branch") String defaultBranch
    ) {
        this.id = id;
        this.nodeId = nodeId;
        this.name = name;
        this.fullName = fullName;
        this.privateRepository = privateRepository;
        this.fork = fork;
        this.owner = owner;
        this.htmlUrl = htmlUrl;
        this.description = description;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.pushedAt = pushedAt;
        this.homepage = homepage;
        this.size = size;
        this.stargazersCount = stargazersCount;
        this.watchersCount = watchersCount;
        this.language = language;
        this.forksCount = forksCount;
        this.openIssuesCount = openIssuesCount;
        this.license = license;
        this.allowForking = allowForking;
        this.isTemplate = isTemplate;
        this.topics = topics;
        this.visibility = visibility;
        this.defaultBranch = defaultBranch;
    }

    @Getter
    @ToString
    public static class Owner {
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

        @JsonCreator
        public Owner(
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
                @JsonProperty("site_admin") boolean siteAdmin
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
        }
    }

    @Getter
    @ToString
    public static class License {
        private final String key;
        private final String name;
        private final String spdxId;
        private final String url;
        private final String nodeId;

        @JsonCreator
        public License(
                @JsonProperty("key") String key,
                @JsonProperty("name") String name,
                @JsonProperty("spdx_id") String spdxId,
                @JsonProperty("url") String url,
                @JsonProperty("node_id") String nodeId
        ) {
            this.key = key;
            this.name = name;
            this.spdxId = spdxId;
            this.url = url;
            this.nodeId = nodeId;
        }
    }
}
