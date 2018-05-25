package com.practice.springcloud.gateway;

import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LuoBaoding on 2018/5/8
 */
@Configuration
public class DefaultRibbonConfig {

    @Bean
    public IRule ribbonRule() {
        /** in prod should use BestAvailableRule */
        return new BestAvailableRule();
//    return new AvailabilityFilteringRule();
//        return new RoundRobinRule();

    }

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }
}
