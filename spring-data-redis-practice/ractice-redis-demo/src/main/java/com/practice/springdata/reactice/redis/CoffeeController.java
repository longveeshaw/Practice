package com.practice.springdata.reactice.redis;

import com.practice.springdata.reactice.redis.modle.Coffee;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Luo Bao Ding
 * @since 2018/5/29
 */
@RestController
public class CoffeeController {
    private final ReactiveRedisOperations<String, Coffee> coffeeOps;

    public CoffeeController(ReactiveRedisOperations<String, Coffee> coffeeOps) {
        this.coffeeOps = coffeeOps;
    }

    @RequestMapping("/coffees")
    public Flux<Coffee> all() {
        return coffeeOps.keys("*").flatMap(coffeeOps.opsForValue()::get);
    }
}
