package com.example.redis.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final StringRedisTemplate redisTemplate;

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @PostMapping("/set-fruit")
    public String setFruit(@RequestParam String name) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("fruit", name);

        return "saved";
    }

    @GetMapping("/get-fruit")
    public String setFruit() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String fruitName = ops.get("fruit");
        return fruitName;
    }
}
