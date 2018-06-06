package com.practice.springcloud.gateway.filter_factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.tuple.Tuple;

import java.net.URI;

/**
 * @author Luo Bao Ding
 * @since 2018/5/29
 */
@Component
public class AuthGatewayFilterFactory implements GatewayFilterFactory {

    @Override
    public GatewayFilter apply(Tuple args) {

        return (exchange, chain) -> {
// ignore
            return chain.filter(exchange);
        };
    }

    public GatewayFilter apply2(Tuple args) {
        return (exchange, chain) ->
                exchange.getFormData()
                        .flatMap(formData -> {
                            String username = formData.getFirst("username");
                            String password = formData.getFirst("password");

                            //...

                            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                    .uri(URI.create("xx"))
                                    .build();

                            return chain.filter(
                                    exchange
                                            .mutate()
                                            .request(modifiedRequest)
                                            .build()
                            );
                        });
    }
}
