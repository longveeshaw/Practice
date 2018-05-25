package com.practice.springcloud.ribbon.client.user.service.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@CommonsLog
@Service
public class BookService {

    private final RestTemplate restTemplate;

    public BookService(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public String readingList() {
        log.info("Access /say-hello/recommended");
        return this.restTemplate.getForObject("http://say-hello/say-hello/recommended", String.class);
    }

    public String reliable() {
        return "fall back";
    }

}