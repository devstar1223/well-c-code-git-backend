package com.wccg.well_c_code_git_backend.global.github.client;

import com.wccg.well_c_code_git_backend.global.github.dto.GithubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GithubUserClient {

    private final WebClient githubApiWebClient;

    public GithubUserResponse requestUserInfo(String accessToken) {
        return githubApiWebClient
                .get()
                .uri("/user")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GithubUserResponse.class)
                .block();
    }
}
