package com.practice.springcloud.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloFeignClient helloFeignClient;

    @RequestMapping("/greeting")
    public String hello() {
        return helloFeignClient.greeting();
    }
}
