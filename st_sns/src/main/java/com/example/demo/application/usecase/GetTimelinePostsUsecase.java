package com.example.demo.application.usecase;

import com.example.demo.domain.follow.dto.FollowDto;
import com.example.demo.domain.follow.service.FollowReadService;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.Timeline;
import com.example.demo.domain.post.service.PostReadService;
import com.example.demo.domain.post.service.TimelineReadService;
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
    private final TimelineReadService timelineReadService;

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

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        /*
            1. Timeline 테이블 조회
            2. 1번에 해당하는 게시물을 조회한다.
         */
        PageCursor<Timeline> pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        List<Long> postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
        List<Post> posts = postReadService.getPosts(postIds);

        return new PageCursor<>(pagedTimelines.nextCursorRequest(), posts);
    }

}
