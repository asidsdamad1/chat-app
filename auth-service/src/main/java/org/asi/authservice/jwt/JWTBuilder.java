package org.asi.authservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.asi.authservice.security.SecurityUserDetailsImpl;
import org.asi.authservice.security.model.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.stream.Collectors;

import static org.asi.authservice.jwt.JWTConstants.AUTHORITIES_KEY;
import static org.asi.authservice.jwt.JWTConstants.USERNAME_KEY;

@Configuration
@RequiredArgsConstructor
public class JWTBuilder {
    private final JWTConfig jwtConfig;

    public String buildToken(Authentication authentication) {
        var user = (CustomUserDetails) authentication.getPrincipal();
        var authorities = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        var expiresAt = new Date(System.currentTimeMillis() + jwtConfig.getExpireTime());

        return JWT.create()
                .withSubject(String.valueOf(user.getUser().getId()))
                .withExpiresAt(expiresAt)
                .withClaim(USERNAME_KEY, user.getUsername())
                .withClaim(AUTHORITIES_KEY, authorities)
                .sign(Algorithm.HMAC512(jwtConfig.getSecretKey()));
    }

}
