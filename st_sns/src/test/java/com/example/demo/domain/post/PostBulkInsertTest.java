package com.example.demo.domain.post;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.jdbcRepository.PostRepository;
import com.example.demo.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName("id를 지정하여 게시물 작성하기")
    @Test
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(1900, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Post> posts = IntStream.range(0, 3_000_000)
                .parallel()
                .mapToObj(i ->  easyRandom.nextObject(Post.class))
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간" + stopWatch.getTotalTimeSeconds());

        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("DB 인서트 시간" + queryStopWatch.getTotalTimeSeconds());
    }
}