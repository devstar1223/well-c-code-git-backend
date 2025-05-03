package com.wccg.well_c_code_git_backend.global.github.client;

import com.wccg.well_c_code_git_backend.global.github.dto.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubRepositoryClient {

    private final WebClient githubApiWebClient;
    private static final int PER_PAGE = 100;

    public List<GithubRepositoryResponse> getPublicRepositories(String accessToken) {
        List<GithubRepositoryResponse> allRepos = new ArrayList<>();
        int page = 1;

        while (true) {
            final int currentPage = page;
            List<GithubRepositoryResponse> repos = githubApiWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user/repos")
                            .queryParam("visibility", "public")
                            .queryParam("per_page", PER_PAGE)
                            .queryParam("page", currentPage)
                            .build()
                    )
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToFlux(GithubRepositoryResponse.class)
                    .collectList()
                    .blockOptional()
                    .orElse(List.of());

            if (repos.isEmpty()){
                break;
            }

            allRepos.addAll(repos);
            page++;
        }

        return allRepos;
    }
}
