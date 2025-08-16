package org.example.apigateway.config;

import org.example.apigateway.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Value("${bank-service.url}")
    private String bankServiceUrl;
    @Value("${bank-service.id}")
    private String bankServiceId;
    @Value("${bank-service.path}")
    private String bankServicePath;

    @Value("${account-service.url}")
    private String accountServiceUrl;
    @Value("${account-service.id}")
    private String accountServiceId;
    @Value("${account-service.path}")
    private String accountServicePath;

    @Value("${transaction-service.url}")
    private String transactionServiceUrl;
    @Value("${transaction-service.id}")
    private String transactionServiceId;
    @Value("${transaction-service.path}")
    private String transactionServicePath;

    @Value("${auth-service.url}")
    private String authServiceUrl;
    @Value("${auth-service.id}")
    private String authServiceId;
    @Value("${auth-service.path}")
    private String authServicePath;

    private final JwtAuthenticationFilter filter;

    public RouteConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator createRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(bankServiceId, route -> route.path(bankServicePath)
                        .filters(gtf -> gtf.filter(filter)).uri(bankServiceUrl))
                .route(accountServiceId, route -> route.path(accountServicePath)
                        .filters(gtf -> gtf.filter(filter)).uri(accountServiceUrl))
                .route(transactionServiceId, route -> route.path(transactionServicePath)
                        .filters(gtf -> gtf.filter(filter)).uri(transactionServiceUrl))
                .route(authServiceId, route -> route.path(authServicePath).uri(authServiceUrl))
                .build();
    }
}
