package com.example.redis.feature.leaderboard.controller;

import com.example.redis.feature.leaderboard.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LeaderBoardController {

    private final RankingService rankingService;

    @PostMapping("/set-score")
    public Boolean setScore(
            @RequestParam String userId,
            @RequestParam int score
    ) {
        return rankingService.setUserScore(userId, score);
    }

    @GetMapping("/get-score")
    public Long getUserRank(
            @RequestParam String userId
    ) {
        return rankingService.getUserRanking(userId);
    }

    @GetMapping("/get-top-rank")
    public List<String> getTopRank() {
        return rankingService.getTopRanks(5);
    }
}
