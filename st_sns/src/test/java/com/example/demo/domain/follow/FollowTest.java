package com.example.demo.domain.follow;

import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.repository.FollowRepository;
import com.example.demo.util.FollowFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class FollowTest {

    @Autowired
    private FollowRepository followRepository;

    @Test
    void bulkInsert() {
        // id 1 회원의 팔로워를 100만으로 만듦
        EasyRandom easyRandom = FollowFixtureFactory.get(
                1L
        );

        List<Follow> follows = IntStream.range(0, 1_000_000)
                .mapToObj(i -> easyRandom.nextObject(Follow.class))
                .toList();

        followRepository.bulkInsert(follows);
    }
}
