package com.practice.springcloud.gateway.rate_limit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Luo Bao Ding
 * @since 2018/5/15
 */
@CommonsLog
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitByIpGatewayFilter implements GatewayFilter, Ordered {
    private boolean enableRateLimit = true;
    private int capacity;
    private int refillTokens;
    private Duration refillDuration;
    private static final Map<String, Bucket> CACHE = new ConcurrentHashMap<>();

    private Bucket createBucket() {
        Refill refill = Refill.of(refillTokens, refillDuration);
        Bandwidth bandwidth = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(bandwidth).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!enableRateLimit) {
            return chain.filter(exchange);
        }
        String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
        Bucket bucket = CACHE.computeIfAbsent(ip, f -> createBucket());
        log.info("aaaaaaaaaaa ip: " + ip + " , token bucket available tokens before consume : " + bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
