package com.example.demo.domain.post;

import com.example.demo.application.usecase.CreatePostUsecase;
import com.example.demo.application.usecase.GetTimelinePostsUsecase;
import com.example.demo.domain.post.dto.PostCommand;
import com.example.demo.util.CursorRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;


@SpringBootTest
public class PostTest {

    @Autowired
    private CreatePostUsecase createPostUsecase;
    @Autowired
    private GetTimelinePostsUsecase getTimelinePostsUsecase;

    @DisplayName("팔로워가 많은 회원의 게시물 작성 성능 테스트(Fan Out On Write / Push Model")
    @Test
    void createPostPushModel() {
        PostCommand command = new PostCommand(
                1L,
                "test contents"
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

    @DisplayName("팔로우한 사람이 많은 회원의 타임라인 요청(read) 성능 테스트(Fan Out On Write / Pull Model")
    @Test
    void createPostPullModel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CursorRequest cursorRequest = new CursorRequest(null, 10);
//        getTimelinePostsUsecase.executeByTimeline(2L, cursorRequest);
        getTimelinePostsUsecase.execute(2L, cursorRequest);

        stopWatch.stop();
        System.out.println("팔로우를 많이한 회원의 게시물 read 시간 : " + stopWatch.getTotalTimeSeconds());
        /*
            Fan Out On read 모델의 read 시간 : 4.434585625
            Fan Out On Write 경우 read 시간 : 0.34387675
         */
    }
}
