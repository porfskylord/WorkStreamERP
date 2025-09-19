package com.wserp.apigateway.config;

import com.wserp.apigateway.enums.GatewayStatus;
import com.wserp.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
            return Mono.just(ip);
        };
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(
                10,
                20,
                1
        );
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.stripPrefix(1).filter(filter)
                                .circuitBreaker(config -> config
                                        .setName("authServiceCircuitBreaker")
                                        .setFallbackUri("forward:/authFallback")
                                        .setStatusCodes(Set.of(
                                                GatewayStatus.BAD_GATEWAY.toString(),
                                                GatewayStatus.INTERNAL_SERVER_ERROR.toString(),
                                                GatewayStatus.SERVICE_UNAVAILABLE.toString(),
                                                GatewayStatus.GATEWAY_TIMEOUT.toString()
                                        ))
                                )
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())
                                        .setStatusCode(HttpStatus.TOO_MANY_REQUESTS)
                                )
                                //.metadata(Map.of("responseTimeout", ""))
                        )
                        .uri("lb://AUTH-SERVICE:8081"))

                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.stripPrefix(1).filter(filter)
                                .circuitBreaker(config -> config
                                        .setName("userServiceCircuitBreaker")
                                        .setFallbackUri("forward:/userFallback")
                                        .setStatusCodes(Set.of(
                                                GatewayStatus.BAD_GATEWAY.toString(),
                                                GatewayStatus.INTERNAL_SERVER_ERROR.toString(),
                                                GatewayStatus.SERVICE_UNAVAILABLE.toString(),
                                                GatewayStatus.GATEWAY_TIMEOUT.toString()
                                        ))
                                )
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())
                                        .setStatusCode(HttpStatus.TOO_MANY_REQUESTS)
                                )
                                .metadata(Map.of("responseTimeout", "10000"))

                        )
                        .uri("lb://USER-SERVICE:8082"))

                .route("project-service", r -> r.path("/api/projects/**")
                        .filters(f -> f.stripPrefix(1).filter(filter)
                                .circuitBreaker(config -> config
                                        .setName("projectServiceCircuitBreaker")
                                        .setFallbackUri("forward:/projectFallback")
                                        .setStatusCodes(Set.of(
                                                GatewayStatus.BAD_GATEWAY.toString(),
                                                GatewayStatus.INTERNAL_SERVER_ERROR.toString(),
                                                GatewayStatus.SERVICE_UNAVAILABLE.toString(),
                                                GatewayStatus.GATEWAY_TIMEOUT.toString()))
                                )
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())
                                        .setStatusCode(HttpStatus.TOO_MANY_REQUESTS)
                                )
                                .metadata(Map.of("responseTimeout", "10000"))
                        )
                        .uri("lb://PROJECT-SERVICE:8083"))
                .build();
    }
}
