package com.wccg.well_c_code_git_backend.global.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "jwt")
@Setter
public class JwtProperties {
    private String secret;
    @Getter
    private long jwtAccessTokenValidityInMillis;

    @Getter
    private List<String> excludePaths;

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}