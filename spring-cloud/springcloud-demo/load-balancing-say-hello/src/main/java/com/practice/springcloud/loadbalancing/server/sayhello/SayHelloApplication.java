package com.practice.springcloud.loadbalancing.server.sayhello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@EnableDiscoveryClient
@RestController
@SpringBootApplication
public class SayHelloApplication {
    private static Logger logger = LoggerFactory.getLogger(SayHelloApplication.class);

    @RequestMapping("/greeting")
    public String greeting() {
        logger.info("access to greeting");

        List<String> helloList = Arrays.asList("Hi there", "Greetings", "Salutations");
        return helloList.get(new Random().nextInt(helloList.size()));
    }

    @RequestMapping("/")
    public String home() {
        logger.info("access to home");
        return "hello!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SayHelloApplication.class, args);
    }
}
