package com.practice.springcloud.ribbon.server.sayhello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class SayHelloApplication {

    private static Logger log = LoggerFactory.getLogger(SayHelloApplication.class);

    @RequestMapping(value = "/greeting")
    public String greet() {
        log.info("Access /greeting");

        List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
        Random rand = new Random();

        int randomNum = rand.nextInt(greetings.size());
        return greetings.get(randomNum);
    }

    @RequestMapping(value = "/")
    public String home() {
        log.info("Access /");
        return "Hi!";
    }

    @RequestMapping(value = "/recommended")
    public String readingList() {
        log.info("Access /sayhello/recommended");
        return "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";
    }

    @RequestMapping("/sleep/{timeInSeconds}")
    public String sleep(@PathVariable("timeInSeconds") int timeInSeconds) {
        try {
            log.info("Access /sleep/"+timeInSeconds);

            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success accessed /sleep/"+timeInSeconds;

    }

    public static void main(String[] args) {
        SpringApplication.run(SayHelloApplication.class, args);
    }
}