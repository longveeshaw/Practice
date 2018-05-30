package com.practice.springcloud.loadbalancing.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
@RibbonClient(name = "say-hello", configuration = SayHelloConfiguration.class)
public class UserApplication {
    @Autowired
    private RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping("/hi")
    public String hi(@RequestParam(value = "username", defaultValue = "John") String username) {
        String greeting = this.restTemplate.getForObject("http://say-hello/greeting", String.class);
        return String.format("%s, %s !", greeting, username);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
