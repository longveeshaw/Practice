package com.practice.spring.webflux.operate_demo.copy_body;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static reactor.core.scheduler.Schedulers.single;

@Slf4j
class PartnerServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private final StringWriter cachedCopy = new StringWriter();

    PartnerServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody()
            .publishOn(single())
            .doOnNext(this::cache)
            .doOnComplete(() -> trace(getDelegate(), cachedCopy.toString()));
    }

    private void cache(DataBuffer buffer) {
        cachedCopy.write(new String(buffer.asByteBuffer().array(), UTF_8));
    }

    private void trace(ServerHttpRequest request, String requestBody) {
        log.info(requestBody);
    }
}