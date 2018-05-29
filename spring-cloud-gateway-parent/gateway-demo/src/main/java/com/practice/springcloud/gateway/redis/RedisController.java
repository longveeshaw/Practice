package com.practice.springcloud.gateway.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    @Autowired
    @Qualifier("redisOperations")
    private ReactiveRedisOperations<String, String> tokenOps;


    @PostMapping("/set/{key}/{val}")
    public Object set(@PathVariable("key") String key, @PathVariable("val") String val) {
        return tokenOps.opsForValue().set(key, val);
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable("key") String key) {
        return tokenOps.opsForValue().get(key);
    }

    @GetMapping("isOk")
    public Object get() {
        return "success";
    }
}
