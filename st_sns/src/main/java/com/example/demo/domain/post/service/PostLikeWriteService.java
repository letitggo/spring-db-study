package com.example.demo.domain.post.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.PostLike;
import com.example.demo.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeWriteService {
    private final PostLikeRepository postLikeRepository;

    public Long create(Post post, MemberDto memberDto) {
        PostLike postLike = PostLike
                .builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();
        return postLikeRepository.save(postLike).getPostId();
    }
}
