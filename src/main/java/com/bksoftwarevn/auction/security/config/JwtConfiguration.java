package com.bksoftwarevn.auction.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "security.authentication.jwt")
@Configuration
@Data
public class JwtConfiguration {
    private String secret;
    private String base64Secret;
    private long tokenValidityInSeconds;
    private long tokenValidityInSecondsForRememberMe;
    private String contentSecurityPolicy = "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";

}
