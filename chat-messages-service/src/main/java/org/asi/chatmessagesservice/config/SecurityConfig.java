package org.asi.chatmessagesservice.config;

import org.asi.authutils.jwt.JWTConfig;
import org.asi.authutils.jwt.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveAuthenticationManager jwtAuthenticationManager,
                                                         ServerAuthenticationConverter jwtAuthenticationConverter) {
        http
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .cors(cors -> {
                })
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.authenticationManager(jwtAuthenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        http.addFilterBefore(authenticationWebFilter(jwtAuthenticationManager, jwtAuthenticationConverter),
                SecurityWebFiltersOrder.AUTHENTICATION);

        http.authorizeExchange(auth -> auth
                .pathMatchers(SWAGGER_AUTH_WHITELIST).permitAll()
                .anyExchange().authenticated()
        );

        return http.build();
    }


    @Bean
    public AuthenticationWebFilter authenticationWebFilter(ReactiveAuthenticationManager jwtAuthenticationManager,
                                                           ServerAuthenticationConverter jwtAuthenticationConverter) {
        var authenticationWebFilter = new AuthenticationWebFilter(jwtAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(jwtAuthenticationConverter);
        return authenticationWebFilter;
    }

    @Bean
    public JWTConfig jwtConfig() {
        return new JWTConfig();
    }

    @Bean
    public JWTUtils jwtUtils() {
        return new JWTUtils(jwtConfig());
    }


}
