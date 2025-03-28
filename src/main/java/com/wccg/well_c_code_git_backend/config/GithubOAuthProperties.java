package com.wccg.well_c_code_git_backend.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "github")
@Getter
public class GithubOAuthProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
