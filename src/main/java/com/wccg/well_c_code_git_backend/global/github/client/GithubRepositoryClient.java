package com.wccg.well_c_code_git_backend.global.github.client;

import com.wccg.well_c_code_git_backend.global.github.dto.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubRepositoryClient {

    private final WebClient githubApiWebClient;

    public List<GithubRepositoryResponse> getPublicRepositories(String accessToken) {
        return githubApiWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/repos")
                        .queryParam("visibility", "public")
                        .build()
                )
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToFlux(GithubRepositoryResponse.class)
                .collectList()
                .blockOptional()
                .orElse(List.of());
    }
}
