package com.wccg.well_c_code_git_backend.global.github.client;

import com.wccg.well_c_code_git_backend.global.github.dto.GithubBranchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubBranchClient {

    private final WebClient githubApiWebClient;
    private static final int PER_PAGE = 100;

    public List<GithubBranchResponse> getBranches(String accessToken, String owner, String repo) {
        List<GithubBranchResponse> allBranches = new ArrayList<>();
        int page = 1;

        while (true) {
            final int currentPage = page;

            List<GithubBranchResponse> branches = githubApiWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/repos/{owner}/{repo}/branches")
                            .queryParam("per_page", PER_PAGE)
                            .queryParam("page", currentPage)
                            .build(owner, repo)
                    )
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToFlux(GithubBranchResponse.class)
                    .collectList()
                    .blockOptional()
                    .orElse(List.of());

            if (branches.isEmpty()){
                break;
            }

            allBranches.addAll(branches);
            page++;
        }

        return allBranches;
    }
}
