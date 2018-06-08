package com.practice.springcloud.gateway.filter_factory;

import com.fasterxml.jackson.databind.JsonNode;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.HttpMessageWriterResponse;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.cloud.gateway.filter.factory.rewrite.RewriteUtils.process;

/**
 *
 * curl -X POST -H "Content-Type: application/json" -d '{"uid": "1","sign":"e9c4dbf01b63c06e7327c0c6f19ea443"}' "http://localhost:8080/rb/post"
 *
 * @author Luo Bao Ding
 * @since 2018/6/8
 */
@Component
public class ReadBodyGatewayFilterFactory extends AbstractGatewayFilterFactory {
    private final ServerCodecConfigurer codecConfigurer;

    public ReadBodyGatewayFilterFactory(ServerCodecConfigurer codecConfigurer) {
        this.codecConfigurer = codecConfigurer;
    }


    @Override
    public GatewayFilter apply(Object object) {

        return (exchange, chain) -> {
            Class inClass = JsonNode.class;
            MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
            ResolvableType inElementType = ResolvableType.forClass(inClass);
            Optional<HttpMessageReader<?>> reader = RewriteUtils.getHttpMessageReader(codecConfigurer, inElementType, mediaType);

            if (reader.isPresent()) {
                Mono<Object> readMono = reader.get()
                        .readMono(inElementType, exchange.getRequest(), Collections.emptyMap())
                        .cast(Object.class);
                return process(readMono, peek -> {
                    // note : peek is the original request body
                    ResolvableType outElementType = ResolvableType
                            .forClass(JsonNode.class);
                    Optional<HttpMessageWriter<?>> writer = RewriteUtils.getHttpMessageWriter(codecConfigurer, outElementType, mediaType);

                    if (writer.isPresent()) {
//                        Object data = config.rewriteFunction.apply(exchange, peek);
// [[[[[[[[[[[[[[[[[[ read body,   biz
                        // TODO: 2018/6/8  read body
                        JsonNode jsonNode = (JsonNode) peek;
                        String uid = jsonNode.get("uid").asText();
                        String sign = jsonNode.get("sign").asText();
                        System.out.println("uid = " + uid);
                        System.out.println("sign = " + sign);
// read body,   biz ]]]]]]]]]]]]]]]]]]]]
                        //TODO: deal with multivalue? ie Flux
                        Publisher publisher = Mono.just(peek);

                        HttpMessageWriterResponse fakeResponse = new HttpMessageWriterResponse(exchange.getResponse().bufferFactory());
                        writer.get().write(publisher, inElementType, mediaType,
                                fakeResponse, Collections.emptyMap());
                        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                                exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                HttpHeaders httpHeaders = new HttpHeaders();
                                httpHeaders.putAll(super.getHeaders());
                                // TODO: this causes a 'HTTP/1.1 411 Length Required' on
                                // httpbin.org
                                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                if (fakeResponse.getHeaders().getContentType() != null) {
                                    httpHeaders.setContentType(
                                            fakeResponse.getHeaders().getContentType());
                                }
                                return httpHeaders;
                            }

                            @Override
                            public Flux<DataBuffer> getBody() {
                                return (Flux<DataBuffer>) fakeResponse.getBody();
                            }
                        };
                        return chain.filter(exchange.mutate().request(decorator).build());
                    }
                    return chain.filter(exchange);
                });

            }


// TODO: 2018/6/8
            return chain.filter(exchange);
        };
    }
}
