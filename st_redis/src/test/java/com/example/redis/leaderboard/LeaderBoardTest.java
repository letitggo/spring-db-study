package com.example.redis.leaderboard;

import com.example.redis.feature.leaderboard.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class LeaderBoardTest {

    @Autowired
    private RankingService rankingService;

    @Test
    void insertScore() {
        for (int i = 0; i < 1_000_000; i++) {
            int score = (int) (Math.random() * 1_000_000);
            String userId = "user_" + i;
            rankingService.setUserScore(userId, score);
        }
    }

    @Test
    void getRanks() {
        rankingService.getTopRanks(1);

        // 1
        Instant before = Instant.now();
        Long userRank = rankingService.getUserRanking("user_100");
        Duration elapsed = Duration.between(before, Instant.now());
        System.out.println("Rank: " + userRank + ", " + elapsed.getNano() / 1_000_000 + "ms");  // Rank: 707528, 1ms

        // 2
        before = Instant.now();
        List<String> topRanks = rankingService.getTopRanks(10);
        elapsed = Duration.between(before, Instant.now());
        System.out.println("Range " + elapsed.getNano() / 1_000_000 + "ms");    // Range 1ms
    }

    @Test
    void inMemorySortPerformance() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            int score = (int) (Math.random() * 1_000_000);
            list.add(score);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // NLongN
        Collections.sort(list);

        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");  // 173ms
    }
}
