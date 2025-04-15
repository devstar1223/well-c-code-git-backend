package com.wccg.well_c_code_git_backend.global.github.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@ToString
public class GithubCommitResponse {

    private final String sha;
    private final String nodeId;
    private final Commit commit;
    private final String url;
    private final String htmlUrl;
    private final String commentsUrl;
    private final Author author;
    private final Committer committer;
    private final List<Parent> parents;

    @JsonCreator
    public GithubCommitResponse(
            @JsonProperty("sha") String sha,
            @JsonProperty("node_id") String nodeId,
            @JsonProperty("commit") Commit commit,
            @JsonProperty("url") String url,
            @JsonProperty("html_url") String htmlUrl,
            @JsonProperty("comments_url") String commentsUrl,
            @JsonProperty("author") Author author,
            @JsonProperty("committer") Committer committer,
            @JsonProperty("parents") List<Parent> parents
    ) {
        this.sha = sha;
        this.nodeId = nodeId;
        this.commit = commit;
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.commentsUrl = commentsUrl;
        this.author = author;
        this.committer = committer;
        this.parents = parents;
    }

    @Getter
    @ToString
    public static class Commit {
        private final CommitAuthor author;
        private final CommitCommitter committer;
        private final String message;
        private final Tree tree;
        private final String url;
        private final int commentCount;
        private final Verification verification;

        @JsonCreator
        public Commit(
                @JsonProperty("author") CommitAuthor author,
                @JsonProperty("committer") CommitCommitter committer,
                @JsonProperty("message") String message,
                @JsonProperty("tree") Tree tree,
                @JsonProperty("url") String url,
                @JsonProperty("comment_count") int commentCount,
                @JsonProperty("verification") Verification verification
        ) {
            this.author = author;
            this.committer = committer;
            this.message = message;
            this.tree = tree;
            this.url = url;
            this.commentCount = commentCount;
            this.verification = verification;
        }

        @Getter
        @ToString
        public static class CommitAuthor {
            private final String name;
            private final String email;
            private final OffsetDateTime date;

            @JsonCreator
            public CommitAuthor(
                    @JsonProperty("name") String name,
                    @JsonProperty("email") String email,
                    @JsonProperty("date") OffsetDateTime date
            ) {
                this.name = name;
                this.email = email;
                this.date = date;
            }
        }

        @Getter
        @ToString
        public static class CommitCommitter {
            private final String name;
            private final String email;
            private final OffsetDateTime date;

            @JsonCreator
            public CommitCommitter(
                    @JsonProperty("name") String name,
                    @JsonProperty("email") String email,
                    @JsonProperty("date") OffsetDateTime date
            ) {
                this.name = name;
                this.email = email;
                this.date = date;
            }
        }

        @Getter
        @ToString
        public static class Tree {
            private final String sha;
            private final String url;

            @JsonCreator
            public Tree(
                    @JsonProperty("sha") String sha,
                    @JsonProperty("url") String url
            ) {
                this.sha = sha;
                this.url = url;
            }
        }

        @Getter
        @ToString
        public static class Verification {
            private final boolean verified;
            private final String reason;
            private final String signature;
            private final String payload;
            private final String verifiedAt;

            @JsonCreator
            public Verification(
                    @JsonProperty("verified") boolean verified,
                    @JsonProperty("reason") String reason,
                    @JsonProperty("signature") String signature,
                    @JsonProperty("payload") String payload,
                    @JsonProperty("verified_at") String verifiedAt
            ) {
                this.verified = verified;
                this.reason = reason;
                this.signature = signature;
                this.payload = payload;
                this.verifiedAt = verifiedAt;
            }
        }
    }

    @Getter
    @ToString
    public static class Author {
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
        private final String userViewType;
        private final boolean siteAdmin;

        @JsonCreator
        public Author(
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
                @JsonProperty("user_view_type") String userViewType,
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
            this.userViewType = userViewType;
            this.siteAdmin = siteAdmin;
        }
    }

    @Getter
    @ToString
    public static class Committer {
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
        private final String userViewType;
        private final boolean siteAdmin;

        @JsonCreator
        public Committer(
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
                @JsonProperty("user_view_type") String userViewType,
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
            this.userViewType = userViewType;
            this.siteAdmin = siteAdmin;
        }
    }

    @Getter
    @ToString
    public static class Parent {
        private final String sha;
        private final String url;
        private final String htmlUrl;

        @JsonCreator
        public Parent(
                @JsonProperty("sha") String sha,
                @JsonProperty("url") String url,
                @JsonProperty("html_url") String htmlUrl
        ) {
            this.sha = sha;
            this.url = url;
            this.htmlUrl = htmlUrl;
        }
    }
}
