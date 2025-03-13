package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.DailyPostCount;
import com.example.demo.domain.post.dto.DailyPostCountRequest;
import com.example.demo.domain.post.dto.PostDto;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostJpaRepository;
import com.example.demo.domain.post.repository.PostLikeJpaRepository;
import com.example.demo.util.CursorRequest;
import com.example.demo.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {

    private final PostJpaRepository postJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
            firstDate ~ lastDate 까지 작성한 글 수 확인하기
            반환 값 -> 리스트[일자, 작성자, 게시글수]
            select createdDate, memberId, count(id) from post
            where memberId = :memberId AND createdDate BETWEEN :firstdate AND :lastDate
            GROUP BY createdDate memberId
         */

        return postJpaRepository.groupByCreatedDate(request.memberId(), request.firstDate(), request.lastDate());
    }

    public Post getPost(Long postId) {
        return postJpaRepository.findById(postId).orElseThrow();
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        return postJpaRepository.findAllByMemberId(memberId, pageable)
                .map(post -> PostDto.toDto(post, postLikeJpaRepository.countPostLikesByPostId(post.getId())));

    }

    public List<Post> getPosts(List<Long> ids) {
        return postJpaRepository.findAllByIdIn(ids);
    }

    // 커서 기반 페이지네이션
    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        List<Post> posts = findAllBy(memberId, cursorRequest);
        long nextKey = getNextKey(cursorRequest, posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        List<Post> posts = findAllBy(memberIds, cursorRequest);
        long nextKey = getNextKey(cursorRequest, posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    // 커서 기반 페이지네이션
    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postJpaRepository.findAllByMemberIdAndIdLessThanOrderByIdDesc(memberId, cursorRequest.key(), Limit.of(cursorRequest.size()));
        } else {
            return postJpaRepository.findAllByMemberIdOrderByIdDesc(memberId, Limit.of(cursorRequest.size()));
        }
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postJpaRepository.findAllByMemberIdInAndIdLessThanOrderByIdDesc(memberIds, cursorRequest.key(), Limit.of(cursorRequest.size()));
        } else {
            return postJpaRepository.findAllByMemberIdInOrderByIdDesc(memberIds, Limit.of(cursorRequest.size()));
        }
    }

    private static long getNextKey(CursorRequest cursorRequest, List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(cursorRequest.NONE_KEY);
    }
}
