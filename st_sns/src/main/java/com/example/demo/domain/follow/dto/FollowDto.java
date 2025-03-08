package com.example.demo.domain.follow.dto;

import com.example.demo.domain.follow.entity.Follow;

import java.time.LocalDateTime;

public record FollowDto(
        Long id,
        Long fromMemberId,
        Long toMemberId,
        LocalDateTime createdAt
) {
    public static FollowDto toDto(Follow follow) {
        return new FollowDto(
                follow.getId(),
                follow.getFromMemberId(),
                follow.getToMemberId(),
                follow.getCreatedAt()
        );
    }
}
