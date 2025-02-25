package org.asi.messageswebsocketservice.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asi.authutils.jwt.JWTConfig;
import org.asi.authutils.jwt.JWTUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthService {

    private final JWTUtils jwtUtils;
    private final JWTConfig jwtConfig;

    public UsernamePasswordAuthenticationToken attemptAuthentication(String authorizationHeaderValue) {

        if (jwtUtils.isValidAuthorizationHeaderValue(authorizationHeaderValue)) {
            try {
                var token = authorizationHeaderValue.replace(jwtConfig.getTokenPrefix(), "");
                return jwtUtils.getAuthentication(token);
            } catch (JWTDecodeException | TokenExpiredException ex) {
                log.error("Invalid token");
            }
        }
        return null;
    }


}
