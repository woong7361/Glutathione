package com.example.gatewayservice.filter;

import com.example.gatewayservice.service.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class AuthenticationHeaderFilter extends AbstractGatewayFilterFactory<AuthenticationHeaderFilter.Config> {
    public static final String DEFAULT_ERROR_RESPONSE = "The requested token is invalid.";
    public static final String NO_AUTHORIZATION_HEADER = "No authorization header";
    public static final String JWT_TOKEN_IS_NOT_VALID = "JWT token is not valid";
    public static final String BEARER_ = "Bearer ";

    private final JwtService jwtService;

    public AuthenticationHeaderFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Data
    public static class Config {
        private String propertyKey;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (! haveAuthorizationHeader(request)) {
                return onError(exchange, NO_AUTHORIZATION_HEADER, HttpStatus.UNAUTHORIZED);
            }

            String jwt = getJwtFrom(request);

            if (!jwtService.verify(jwt, config.getPropertyKey())) {
                return onError(exchange, JWT_TOKEN_IS_NOT_VALID, HttpStatus.UNAUTHORIZED);
            }

            log.info("authentication successful");

            return chain.filter(exchange);
        };
    }

    private boolean haveAuthorizationHeader(ServerHttpRequest request) {
        return request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private String getJwtFrom(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        return authorizationHeader.replace(BEARER_, "");
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);

        byte[] bytes = DEFAULT_ERROR_RESPONSE.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }
}