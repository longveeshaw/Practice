package com.practice.springcloud.routing.server;

import com.practice.springcloud.routing.server.filters.pre.SimpleFilter;
import com.practice.springcloud.routing.server.loadbalancing.configuration.BookConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@RibbonClient(name = "book", configuration = BookConfiguration.class)
@EnableDiscoveryClient
public class GatewayApplication {

    @Bean
    public SimpleFilter simpleFilter() {
        return new SimpleFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
