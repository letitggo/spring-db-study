package com.example.demo.application.controller;

import com.example.demo.application.usecase.CreatePostLikeUsecase;
import com.example.demo.application.usecase.CreatePostUsecase;
import com.example.demo.application.usecase.GetTimelinePostsUsecase;
import com.example.demo.domain.post.dto.DailyPostCount;
import com.example.demo.domain.post.dto.DailyPostCountRequest;
import com.example.demo.domain.post.dto.PostCommand;
import com.example.demo.domain.post.dto.PostDto;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostReadService;
import com.example.demo.domain.post.service.PostWriteService;
import com.example.demo.util.CursorRequest;
import com.example.demo.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

//    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostsUsecase getTimelinePostsUsecase;
    private final CreatePostUsecase createPostUsecase;
    private final PostWriteService postWriteService;
    private final CreatePostLikeUsecase createPostLikeUsecase;

    //    @PostMapping
//    public PostDto create(@RequestBody PostCommand command) {
//        return postWriteService.create(command);
//    }
    @PostMapping("")
    public Long timeline(@RequestBody PostCommand command) {
        return createPostUsecase.execute(command);
    }

    @GetMapping("/daily-post-count")
    public List<DailyPostCount> getDailyPostCount(
            @ModelAttribute DailyPostCountRequest request
    ) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<PostDto> getPosts(
            @PathVariable Long memberId,
            @PageableDefault Pageable pageable
    ) {
        return postReadService.getPosts(memberId, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getTimeline(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return getTimelinePostsUsecase.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like/v1")
    public void likePostV1(@PathVariable Long postId) {
//        postWriteService.likeCount(postId);
        // 낙관적 락
        postWriteService.likeCountByOptimisticLock(postId);
    }

    @PostMapping("/{postId}/like/v2")
    public void likePostV2(@PathVariable Long postId, @RequestParam Long memberId) {
        /*
            read의 성능은 떨어지지만 write 성능이 좋아진다.
            + 하나의 작업에 계속 update를 하려고 경합하지 않는다.
            + 따라서 post에 대한 락을 잡지 않아도 된다.(insert이기 때문에)
        */
        createPostLikeUsecase.execute(memberId, postId);
    }
}