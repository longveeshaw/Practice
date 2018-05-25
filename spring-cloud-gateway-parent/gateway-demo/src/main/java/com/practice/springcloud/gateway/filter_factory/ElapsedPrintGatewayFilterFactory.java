package com.practice.springcloud.gateway.filter_factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 可在yaml中配置
 *
 * @author Luo Bao Ding
 * @since 2018/5/14
 */
@Component
public class ElapsedPrintGatewayFilterFactory extends AbstractGatewayFilterFactory<ElapsedPrintGatewayFilterFactory.Config> /*implements Ordered*/ {
    public static Log log = LogFactory.getLog(ElapsedPrintGatewayFilterFactory.class);

    public static final String ELAPSED_TIME_BEGIN = "elapsed_time_begin";
    private static final String KEY = "withParams";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public ElapsedPrintGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                        if (startTime != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("xxxxxxxxxxxxxx")
                                    .append(exchange.getRequest().getURI().getRawPath())
                                    .append(": ")
                                    .append(System.currentTimeMillis() - startTime)
                                    .append(" ms , ")
                            ;
                            if (config.withParams) {
                                sb.append(" params: ")
                                        .append(exchange.getRequest().getQueryParams());
                            }
                            log.info(sb.toString());

                        }
                    }));
        });
    }

/*
//没用
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
*/

    public static class Config {
        public boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }
    }
}
