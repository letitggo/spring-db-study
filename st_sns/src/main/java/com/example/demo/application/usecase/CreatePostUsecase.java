package com.example.demo.application.usecase;

import com.example.demo.domain.follow.dto.FollowDto;
import com.example.demo.domain.follow.service.FollowReadService;
import com.example.demo.domain.post.dto.PostCommand;
import com.example.demo.domain.post.dto.PostDto;
import com.example.demo.domain.post.service.PostWriteService;
import com.example.demo.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {

    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

    /*
        타임라인 같은 경우는 트랜잭션 적용을 할지  안할지 고민해볼만한 문제이다.
        트랜잭션도 결국 Undo Log를 생성하여 이루어지기 때문에 비용이 발생하는 작업이기 때문에
        팔로워가 수백만명이라면 모든 정보를 로그로 담우두어야하기 때문에 비용이 많이 발생할 수 있다.

        트랙잭션 범위는 짧게 가져가는 것이 좋고 트랜잭션 범위를 항상 고려해야한다.
        또한 스프링은 프록시 패턴을 사용하기 때문에 Inner 함수에 트랜잭션을 적용시 작동하지 않는다.

        이미지를 s3에 저장하는, 외부 시스템과 연동된 코드 또한 트랜잭션을 걸게 되면
        트랜잭션을 오랜 시간 잡아두기(네트워크 상태에 따라) 때문에 최대한 배제하는 것이 좋다.

        추가 학습 : propagation()
     */
//    @Transactional
    public Long execute(PostCommand postCommand) {
        PostDto postDto = postWriteService.create(postCommand);

        List<Long> followerMemberIds = followReadService
                .getFollowers(postCommand.memberId())
                .stream()
                .map(FollowDto::fromMemberId)
                .toList();
        timelineWriteService.deliveryToTimeline(postDto.id(), followerMemberIds);
        return postDto.id();
    }
}
