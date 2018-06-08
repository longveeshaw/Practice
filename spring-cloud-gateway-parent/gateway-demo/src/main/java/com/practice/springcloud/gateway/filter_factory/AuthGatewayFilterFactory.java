package com.practice.springcloud.gateway.filter_factory;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Collections;

/**
 * @author Luo Bao Ding
 * @since 2018/5/29
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object object    ) {
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder();
        return
                (exchange, chain) -> {
                    Flux<DataBuffer> body = exchange.getRequest().getBody();
                    return decoder.decode(body, ResolvableType.forClass(JsonNode.class), MediaType.APPLICATION_JSON_UTF8, Collections.emptyMap())
                            .flatMap(obj -> {
                                JsonNode jsonObj = (JsonNode) obj;
                                String uid = jsonObj.get("uid").asText();
                                String sign=jsonObj.get("sign").asText();
                                System.out.println("sign = " + sign);
                                System.out.println("uid = " + uid);
// TODO: 2018/6/7
                                return chain.filter(exchange);//todo change
//                        dataBuffer.asByteBuffer().array();
                            }).publishNext();
                };
    }

    public GatewayFilter apply2(Object args) {
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
