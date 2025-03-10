package com.example.demo.application.usecase;

import com.example.demo.domain.follow.dto.FollowDto;
import com.example.demo.domain.follow.service.FollowReadService;
import com.example.demo.domain.post.dto.PostCommand;
import com.example.demo.domain.post.dto.PostDto;
import com.example.demo.domain.post.service.PostWriteService;
import com.example.demo.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {

    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

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
