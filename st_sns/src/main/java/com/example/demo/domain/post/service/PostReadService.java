package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.DailyPostCount;
import com.example.demo.domain.post.dto.DailyPostCountRequest;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {

    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
            firstDate ~ lastDate 까지 작성한 글 수 확인하기
            반환 값 -> 리스트[일자, 작성자, 게시글수]
            select createdDate, memberId, count(id) from post
            where memberId = :memberId AND createdDate BETWEEN :firstdate AND :lastDate
            GROUP BY createdDate memberId
         */

        return postRepository.groupByCreatedDate(request);
    }

    public Page<Post> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable);
    }
}
