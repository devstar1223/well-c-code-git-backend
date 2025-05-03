package com.wccg.well_c_code_git_backend.global.github.client;

import com.wccg.well_c_code_git_backend.global.github.dto.GithubCommitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubCommitsClient {

    private final WebClient githubApiWebClient;
    private static final int PER_PAGE = 100;

    public List<GithubCommitResponse> getCommits(String accessToken, String owner, String repo, String branch) {
        List<GithubCommitResponse> allCommits = new ArrayList<>();
        int page = 1;

        while (true) {
            final int currentPage = page;

            List<GithubCommitResponse> commits = githubApiWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/repos/{owner}/{repo}/commits")
                            .queryParam("per_page", PER_PAGE)
                            .queryParam("page", currentPage)
                            .queryParam("sha", branch)
                            .build(owner, repo)
                    )
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToFlux(GithubCommitResponse.class)
                    .collectList()
                    .blockOptional()
                    .orElse(List.of());

            if (commits.isEmpty()) {
                break;
            }

            allCommits.addAll(commits);
            page++;
        }

        return allCommits;
    }
}
