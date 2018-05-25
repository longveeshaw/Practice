package com.practice.springcloud.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 貌似只能fluent配置中使用
 *
 * @author Luo Bao Ding
 * @since 2018/5/14
 */
@Component
public class ElapsedFilter implements GatewayFilter, Ordered {
    public static final Log log = LogFactory.getLog(ElapsedFilter.class);

    public static final String ELAPSED_TIME_BEGIN = "elapsed_time_begin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                    if (startTime != null) {
                        log.info("yyyyyyyyyyyyyy" + exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + " ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
