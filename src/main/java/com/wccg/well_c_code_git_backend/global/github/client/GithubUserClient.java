package com.wccg.well_c_code_git_backend.global.github.client;

import com.wccg.well_c_code_git_backend.global.github.dto.GithubUserResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GithubUserClient {
    public GithubUserResponse requestUserInfo(String accessToken) {
        return WebClient.create()
                .get()
                .uri("https://api.github.com/user")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GithubUserResponse.class)
                .block();
    }
}
