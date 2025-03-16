package com.example.redis.service;

import com.example.redis.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ExternalApiService externalApiService;
    private final StringRedisTemplate redisTemplate;

    public UserProfile getUserProfile(String userId) {

        String userName = "";

        // cache aside
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedName = ops.get("nameKey:" + userId);

        if (cachedName != null) {
            userName = cachedName;
        } else {
            userName = externalApiService.getUsername(userId);
            ops.set("nameKey:" + userId, userName, 5, TimeUnit.SECONDS);
        }

        Integer userAge = externalApiService.getUserAge(userId);

        return new UserProfile(userName, userAge);
    }
}
