package com.example.redis.service;

import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public String getUsername(String userId) {
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

    public Integer getUserAge(String userId) {
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
