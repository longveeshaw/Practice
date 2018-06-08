package com.practice.springcloud.gateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * @author Luo Bao Ding
 * @since 2018/6/7
 */
@RestController
public class ReadBodyController {
    /**
     * test:
     * curl --header "Content-Type: application/json" --request POST --data '{"uid":527,"sign":"cc404bad3b2c1809b611b563e0ba72b4","msg":"TEST","msgType":1,"toUid":231}'  http://localhost:8080/gw/post
     */
    @PostMapping(value = {"/gw/post", "/userApi/postData"})
    public Mono<Object> post(ServerHttpRequest request, ServerHttpResponse response) {
        Flux<DataBuffer> body = request.getBody();
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder();

        return decoder.decode(body, ResolvableType.forClass(JsonNode.class), MediaType.APPLICATION_JSON_UTF8, Collections.emptyMap())
                .flatMap(obj -> {
                    JsonNode jsonNode = (JsonNode) obj;
                    String uid = jsonNode.get("uid").asText();
                    String sign = jsonNode.get("sign").asText();
                    System.out.println("sign = " + sign);
                    System.out.println("uid = " + uid);
                    return Mono.just((Object) jsonNode);
                }).next();

    }

}
