package com.wccg.well_c_code_git_backend.infrastructure.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@ConfigurationProperties(prefix = "jwt")
@Setter
public class JwtProperties {
    private String secret;
    @Getter
    private long jwtAccessTokenValidityInMillis;

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}