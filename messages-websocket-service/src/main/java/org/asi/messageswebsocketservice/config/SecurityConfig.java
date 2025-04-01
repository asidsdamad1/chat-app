package org.asi.messageswebsocketservice.config;

import org.asi.authutils.jwt.JWTConfig;
import org.asi.authutils.jwt.JWTUtils;
import org.asi.authutils.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public JWTConfig jwtConfig() {
        return new JWTConfig();
    }

    @Bean
    public JWTUtils jwtUtils(JWTConfig jwtConfig) {
        return new JWTUtils(jwtConfig);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JWTUtils jwtUtils, JWTConfig jwtConfig) {
        return new JwtAuthenticationFilter(jwtUtils, jwtConfig);
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(SWAGGER_AUTH_WHITELIST).permitAll()
                                .requestMatchers("/ws/**", "/topic/**", "/app/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
