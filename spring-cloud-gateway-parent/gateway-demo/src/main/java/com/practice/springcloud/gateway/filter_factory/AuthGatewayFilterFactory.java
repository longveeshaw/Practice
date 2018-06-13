package com.practice.springcloud.gateway.filter_factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author Luo Bao Ding
 * @since 2018/5/29
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {

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
