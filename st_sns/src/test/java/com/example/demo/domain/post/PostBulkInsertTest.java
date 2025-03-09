package com.example.demo.domain.post;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
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

    @Test
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Post> posts = IntStream.range(0, 1_000_000)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
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