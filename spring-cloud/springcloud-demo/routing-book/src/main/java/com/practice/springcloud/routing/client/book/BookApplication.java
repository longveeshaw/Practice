package com.practice.springcloud.routing.client.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class BookApplication {

    @RequestMapping("/available")
    public String available() {
        return "Spring in Action";
    }

    @RequestMapping("/check-out")
    public String checkout() {
        return "Spring boot in Action";
    }

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
