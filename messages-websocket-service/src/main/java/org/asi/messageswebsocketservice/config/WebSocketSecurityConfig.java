package org.asi.messageswebsocketservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.messaging.context.SecurityContextChannelInterceptor;

@Configuration
public class WebSocketSecurityConfig {

    @Bean
    MessageMatcherDelegatingAuthorizationManager.Builder messages() {
        MessageMatcherDelegatingAuthorizationManager.Builder messages = 
            MessageMatcherDelegatingAuthorizationManager.builder();
        
        // Allow all messages without authentication
        messages
            .nullDestMatcher().permitAll()
            .simpSubscribeDestMatchers("/topic/**", "/queue/**").permitAll()
            .simpDestMatchers("/app/**").permitAll()
            .anyMessage().permitAll();
            
        return messages;
    }

    @Bean
    SecurityContextChannelInterceptor securityContextChannelInterceptor() {
        return new SecurityContextChannelInterceptor();
    }
}
