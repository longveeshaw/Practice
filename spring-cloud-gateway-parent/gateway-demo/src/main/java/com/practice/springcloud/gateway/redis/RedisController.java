package com.practice.springcloud.gateway.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisController(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/set/{key}/{val}")
    public Object set(@PathVariable("key") String key,@PathVariable("val")  String val) {
        return redisTemplate.opsForValue().set(key, val);
    }

    @GetMapping("/get")
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
