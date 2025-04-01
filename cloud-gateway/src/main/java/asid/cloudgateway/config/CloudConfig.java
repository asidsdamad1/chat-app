package asid.cloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTH-SERVICE", r -> r.path("/auth-service/**")
                        .filters(f -> f
                                .rewritePath("/auth-service/(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://AUTH-SERVICE")
                )
                .route("MESSAGES-WEBSOCKET", r -> r.path("/messages-websocket/**")
                        .filters(f -> f
                                .rewritePath("/messages-websocket/(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://MESSAGES-WEBSOCKET-SERVICE")
                )
                .route("CHAT-SERVICE", r -> r.path("/chat-service/**")
                        .filters(f -> f
                                .rewritePath("/chat-service/(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://CHAT-SERVICE")
                )
                .route("openapi", r -> r.path("/v3/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/v3/api-docs/?(?<segment>.*)", "/${segment}/v3/api-docs"))
                        .uri("http://localhost:8111")
                )
                .build();
    }
}