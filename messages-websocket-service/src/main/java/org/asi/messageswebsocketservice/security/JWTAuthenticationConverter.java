package org.asi.messageswebsocketservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asi.authutils.jwt.JWTConfig;
import org.asi.authutils.jwt.JWTUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationConverter implements ServerAuthenticationConverter {

    private final JWTUtils jwtUtils;
    private final JWTConfig jwtConfig;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {

        return Mono.justOrEmpty(exchange.getRequest().getHeaders())
                .flatMap(headers -> Mono.justOrEmpty(headers.getFirst(HttpHeaders.AUTHORIZATION)))
                .flatMap(authHeaderValue -> {
                    if (jwtUtils.isValidAuthorizationHeaderValue(authHeaderValue)) {
                        var token = authHeaderValue.replace(jwtConfig.getTokenPrefix(), "");
                        log.debug("Token {}", token);
                        return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
                    } else
                        return Mono.empty();
                });
    }
}
