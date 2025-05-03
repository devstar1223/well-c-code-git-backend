package com.wccg.well_c_code_git_backend.global.github.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GithubBranchResponse {

    private final String name;
    private final Commit commit;
    private final boolean is_protected;

    @JsonCreator
    public GithubBranchResponse(
            @JsonProperty("name") String name,
            @JsonProperty("commit") Commit commit,
            @JsonProperty("protected") boolean is_protected
    ) {
        this.name = name;
        this.commit = commit;
        this.is_protected = is_protected;
    }

    @Getter
    @ToString
    public static class Commit {
        private final String sha;
        private final String url;

        @JsonCreator
        public Commit(
                @JsonProperty("sha") String sha,
                @JsonProperty("url") String url
        ) {
            this.sha = sha;
            this.url = url;
        }
    }
}
