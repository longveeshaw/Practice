package com.practice.springcloud.loadbalancing.client;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 与@SpringBootApplication所在类共package，所以此类也会被扫描，@Bean会生效,且本bundle共享
 */
@Configuration
public class SayHelloConfiguration {
/*
    @Autowired
    private IClientConfig ribbonClientConfig;
*/

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule() {
        return new AvailabilityFilteringRule();
    }
}
