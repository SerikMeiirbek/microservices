package com.amigoscode.apigw.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ApiKryAuthorizationFilter implements GlobalFilter, Ordered {

    private final ApiKeyAuthorizationChecker apiKeyAuthorizationChecker;

    public ApiKryAuthorizationFilter(ApiKeyAuthorizationChecker apiKeyAuthorizationChecker) {
        this.apiKeyAuthorizationChecker = apiKeyAuthorizationChecker;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("ApiAuthorizationFilter... checking the key");

        Route attribute = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String application = attribute.getId();

        List<String> apiKey = exchange.getRequest().getHeaders().get("Api-Key");

        if(application == null || (apiKey == null ||
                apiKey.isEmpty()) ||
                !apiKeyAuthorizationChecker.isAuthorized(apiKey.get(0), application)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you are not authorized");
        }

        System.out.println(apiKey);



        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
