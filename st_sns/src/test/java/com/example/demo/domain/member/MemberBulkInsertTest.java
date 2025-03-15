package com.example.demo.domain.member;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.jdbcRepository.MemberBulkRepository;
import com.example.demo.util.MemberFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberBulkInsertTest {

    @Autowired
    private MemberBulkRepository memberBulkRepository;

    @Test
    void bulkInsert() {
        EasyRandom easyRandom = MemberFixtureFactory.get(
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2025, 1, 1)
        );

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Member> members = IntStream.range(1, 100_000)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Member.class))
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();

        memberBulkRepository.bulkInsert(members);

        queryStopWatch.stop();
        System.out.println("DB 인서트 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}
