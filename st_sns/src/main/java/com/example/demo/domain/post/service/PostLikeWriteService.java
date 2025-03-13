package com.example.demo.domain.post.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.PostLike;
import com.example.demo.domain.post.repository.PostLikeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeWriteService {
    private final PostLikeJpaRepository postLikeJpaRepository;

    public Long create(Post post, MemberDto memberDto) {
        PostLike postLike = PostLike
                .builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();
        return postLikeJpaRepository.save(postLike).getPostId();
    }
}
