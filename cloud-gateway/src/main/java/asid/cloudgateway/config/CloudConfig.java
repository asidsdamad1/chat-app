//package asid.cloudgateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CloudConfig {
//
//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("auth-service", r -> r.path("/auth-service/**")
//                        .filters(f -> f
//                                .rewritePath("/auth-service/(?<remaining>.*)", "/${remaining}"))
//                        .uri("lb://AUTH-SERVICE")
//                )
//                .route("chat-service", r -> r.path("/chat-service/**")
//                        .filters(f -> f.rewritePath("/chat-service/(?<remaining>.*)", "/${remaining}"))
//                        .uri("lb://CHAT")
//                )
//                .route("chat-messages-service", r -> r.path("/chat-messages-service/**")
//                        .filters(f -> f.rewritePath("/chat-messages-service/(?<remaining>.*)", "/${remaining}"))
//                        .uri("lb://CHAT-MESSAGES")
//                )
//                .route("messages-websocket-service", r -> r.path("/messages-websocket-service/**")
//                        .filters(f -> f.rewritePath("/messages-websocket-service/(?<remaining>.*)", "/${remaining}"))
//                        .uri("lb://MESSAGES-WEBSOCKET")
//                )
//                // API docs routes - The key change is here
//                .route("auth-service-docs", r -> r.path("/v3/api-docs/AUTH")
//                        .filters(f -> f.rewritePath("/v3/api-docs/AUTH", "/v3/api-docs"))
//                        .uri("lb://AUTH-SERVICE"))
//                .route("chat-service-docs", r -> r.path("/v3/api-docs/CHAT")
//                        .filters(f -> f.rewritePath("/v3/api-docs/CHAT", "/v3/api-docs"))
//                        .uri("lb://CHAT"))
//                .route("chat-messages-service-docs", r -> r.path("/v3/api-docs/CHAT-MESSAGES")
//                        .filters(f -> f.rewritePath("/v3/api-docs/CHAT-MESSAGES", "/v3/api-docs"))
//                        .uri("lb://CHAT-MESSAGES"))
//                .route("messages-websocket-service-docs", r -> r.path("/v3/api-docs/MESSAGES-WEBSOCKET")
//                        .filters(f -> f.rewritePath("/v3/api-docs/MESSAGES-WEBSOCKET", "/v3/api-docs"))
//                        .uri("lb://MESSAGES-WEBSOCKET"))
//                .build();
//    }
//}