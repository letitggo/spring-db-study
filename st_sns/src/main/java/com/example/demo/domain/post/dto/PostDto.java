package com.example.demo.domain.post.dto;

import com.example.demo.domain.post.entity.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostDto(
        Long id,
        Long memberId,
        String contents,
        LocalDate createdDate,
        LocalDateTime createAt
) {
    public static PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getMemberId(),
                post.getContents(),
                post.getCreatedDate(),
                post.getCreatedAt()
        );
    }
}
