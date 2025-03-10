package com.example.demo.application.usecase;

import com.example.demo.domain.follow.dto.FollowDto;
import com.example.demo.domain.follow.service.FollowReadService;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostReadService;
import com.example.demo.util.CursorRequest;
import com.example.demo.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostsUsecase {

    private final FollowReadService followReadService;
    private final PostReadService postReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /*
            1. memberId -> follow 조회
            2. 1번 결과로 게시물 조회
         */
        List<FollowDto> followings = followReadService.getFollowings(memberId);
        List<Long> toMemberIds = followings.stream()
                .map(FollowDto::toMemberId)
                .toList();

        return postReadService.getPosts(toMemberIds, cursorRequest);
    }

}
