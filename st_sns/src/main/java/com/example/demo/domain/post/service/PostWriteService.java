package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.PostCommand;
import com.example.demo.domain.post.dto.PostDto;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostWriteService {
    private final PostJpaRepository postJpaRepository;

    public PostDto create(PostCommand command) {
        Post saved = postJpaRepository.save(Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build()
        );
        return PostDto.toDto(saved, 0L);
    }

    // 비관적 락
    @Transactional
    public void likeCount(Long postId) {
        Post post = postJpaRepository.findByIdWithLock(postId).orElseThrow();
        post.incrementLikeCount();
    }

    // 낙관적 락(version 비교)
    @Transactional
    public void likeCountByOptimisticLock(Long postId) {
        Post post = postJpaRepository.findByIdWithOptimisticLock(postId).orElseThrow();
        post.incrementLikeCount();
    }
}
