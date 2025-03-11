package com.example.demo.domain.post.dto;

import com.example.demo.domain.post.entity.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostDto(
        Long id,
        Long memberId,
        String contents,
        Long likeCount,
        LocalDate createdDate,
        LocalDateTime createAt
) {
    public static PostDto toDto(Post post, Long postCount) {
        return new PostDto(
                post.getId(),
                post.getMemberId(),
                post.getContents(),
                postCount,
                post.getCreatedDate(),
                post.getCreatedAt()
        );
    }
}
