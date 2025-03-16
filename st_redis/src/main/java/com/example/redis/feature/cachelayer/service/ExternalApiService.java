package com.example.redis.feature.cachelayer.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public String getUsername(String userId) {
        System.out.println("Getting user name from other service..");
        // 외부 서비스나 db 호출(가정)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (userId.equals("A")) {
            return "A";
        }
        if (userId.equals("B")) {
            return "B";
        }

        return "";
    }

    @Cacheable(cacheNames = "userAgeCache", key = "#userId")
    public Integer getUserAge(String userId) {
        System.out.println("Getting user age from other service..");

        // 외부 서비스나 db 호출(가정)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (userId.equals("A")) {
            return 20;
        }
        if (userId.equals("B")) {
            return 30;
        }

        return 0;
    }
}
