package com.example.redis.feature.leaderboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankingService {
    private static final String LEADER_BOARD_KEY = "leaderBoard";

    private final StringRedisTemplate redisTemplate;

    public boolean setUserScore(String userId, int score) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(LEADER_BOARD_KEY, userId, score);
        return true;
    }

    public Long getUserRanking(String userId) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Long rank = zSetOps.reverseRank(LEADER_BOARD_KEY, userId);
        return rank;
    }

    public List<String> getTopRanks(int limit) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<String> range = zSetOps.reverseRange(LEADER_BOARD_KEY, 0, limit);
        return new ArrayList<>(range);
    }
}
