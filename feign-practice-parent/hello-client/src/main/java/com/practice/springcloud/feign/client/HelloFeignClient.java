package com.practice.springcloud.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("say-hello")
public interface HelloFeignClient {

    @RequestMapping("/greeting")
    String greeting();
}
