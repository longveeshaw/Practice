package com.practice.springcloud.routing.server.loadbalancing.configuration;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule() {
        return new AvailabilityFilteringRule();
    }
}
