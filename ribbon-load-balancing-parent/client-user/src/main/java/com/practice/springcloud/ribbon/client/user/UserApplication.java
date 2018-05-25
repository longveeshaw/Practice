package com.practice.springcloud.ribbon.client.user;

import com.practice.springcloud.ribbon.client.user.service.hystrix.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by LuoBaoding on 2018/5/7
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
@RibbonClient(name = "say-hello", configuration = SayHelloConfiguration.class)
@EnableCircuitBreaker
public class UserApplication {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private BookService bookService;

    @RequestMapping("/hi")
    public String hi(@RequestParam(value = "name", defaultValue = "Artaban") String name) {
        String greeting = this.restTemplate.getForObject("http://say-hello/greeting", String.class);
        return String.format("%s, %s!", greeting, name);
    }

    @RequestMapping("/to-read")
    public String readingList() {
        return bookService.readingList();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}