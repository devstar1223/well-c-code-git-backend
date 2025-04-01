package com.wccg.well_c_code_git_backend.infrastructure.jwt;

import com.wccg.well_c_code_git_backend.domain.user.User;
import com.wccg.well_c_code_git_backend.domain.user.UserRole;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private Key secretKey;
    private long jwtAccessTokenValidityInMillis;

    @PostConstruct
    public void init() {
        this.secretKey = jwtProperties.getSecretKey();
        this.jwtAccessTokenValidityInMillis = jwtProperties.getJwtAccessTokenValidityInMillis();
    }

    public String createToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtAccessTokenValidityInMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("role", UserRole.USER)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        return Long.valueOf(
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }
}
