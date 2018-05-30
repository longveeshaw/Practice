package com.practice.springcloud.gateway.filter_factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.tuple.Tuple;

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
}
