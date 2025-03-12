package com.example.demo.domain.post;

import com.example.demo.application.usecase.CreatePostUsecase;
import com.example.demo.domain.post.dto.PostCommand;
import com.example.demo.domain.post.dto.PostDto;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class PostTest {

    @Autowired
    private CreatePostUsecase createPostUsecase;

    @Test
    void createPost() {
        PostCommand command = new PostCommand(
                1L,
                "팔로워가 많은 회원의 게시물 작성 성능 테스트"
        );

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        createPostUsecase.execute(command);

        stopWatch.stop();
        System.out.println("팔로워가 많은 회원의 게시물 작성 시간 : " + stopWatch.getTotalTimeSeconds());
        /*
            팔로워가 많은 회원의 게시물 작성 시간 : 13.021499541
            팔로워가 100만명이기 때문에 게시물 작성시 팔로우한 모든 회원에게 게시물 배달을 하기 위해
            타임라인 또한 100만개가 생성되어 시간이 굉장히 오래 걸린다.
         */
    }
}
