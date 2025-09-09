package com.smartexpense.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class secuGatewayRoutesConfig {

    @Value("${ROUTES_AUTH}")
    private String authServiceUrl;

    @Value("${ROUTES_USERS}")
    private String usersServiceUrl;

    @Value("${ROUTES_TRANSACTIONS}")
    private String transactionsServiceUrl;

    @Value("${ROUTES_CATEGORIZATION}")
    private String categorizationServiceUrl;

    @Value("${ROUTES_FORECASTS}")
    private String forecastsServiceUrl;

    @Bean
    public com.smartexpense.gateway.config.RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .uri(authServiceUrl))
                .route("users-service", r -> r.path("/users/**", "/budgets/**")
                        .uri(usersServiceUrl))
                .route("transactions-service", r -> r.path("/transactions/**")
                        .uri(transactionsServiceUrl))
                .route("categorization-service", r -> r.path("/categories/**", "/rules/**")
                        .uri(categorizationServiceUrl))
                .route("forecasts-service", r -> r.path("/forecasts/**", "/insights/**", "/reports/**")
                        .uri(forecastsServiceUrl))
                .build();
    }
}


