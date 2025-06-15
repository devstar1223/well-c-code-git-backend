package com.wccg.well_c_code_git_backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "JWT";

    @Value("${swagger.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {

        Server server = new Server().url(serverUrl).description("Server");

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("Well-C-Code-Git API 문서")
                        .description("WCCG API 명세서")
                        .version("v1.0.0")
                )
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                        )
                );
    }
}
