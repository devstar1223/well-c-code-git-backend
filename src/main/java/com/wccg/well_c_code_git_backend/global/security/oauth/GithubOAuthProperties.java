package com.wccg.well_c_code_git_backend.global.security.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "github")
@Getter
@Setter
public class GithubOAuthProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;
}
