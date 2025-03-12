package com.example.demo.domain.follow;

import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.repository.FollowRepository;
import com.example.demo.util.FollowFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class FollowBulkInsertTest {

    @Autowired
    private FollowRepository followRepository;

    @DisplayName("팔로워 만들기(id=1 회원의 팔로워를 100만명 만들기)")
    @Test
    void bulkInsertByToMemberId() {
        // id 1 회원의 팔로워를 100만으로 만듦
        EasyRandom easyRandom = FollowFixtureFactory.getByToMemberId(
                1L
        );

        List<Follow> follows = IntStream.range(0, 1_000_000)
                .mapToObj(i -> easyRandom.nextObject(Follow.class))
                .toList();

        followRepository.bulkInsert(follows);
    }

    @DisplayName("팔로우 하기(id=2 회원의 팔로잉을 100만명으로 만들기)")
    @Test
    void bulkInsertByFromMemberId() {
        // id 1 회원의 팔로워를 100만으로 만듦
        EasyRandom easyRandom = FollowFixtureFactory.getByFromMemberId(
                2L
        );

        List<Follow> follows = IntStream.range(0, 1_000_000)
                .mapToObj(i -> easyRandom.nextObject(Follow.class))
                .toList();

        followRepository.bulkInsert(follows);
    }
}
