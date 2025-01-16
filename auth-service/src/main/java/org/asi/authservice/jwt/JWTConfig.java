package org.asi.authservice.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JWTConfig {
    @Value("${security.jwt.uri:/authenticate}")
    private String authEndpoint;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer ")
    private String tokenPrefix;

    @Value("${security.jwt.expire-time:#{24*60*60}}")
    private int expireTime;

    @Value("${security.jwt.secret-key:jwtSecretKey123}")
    private String secretKey;
}
