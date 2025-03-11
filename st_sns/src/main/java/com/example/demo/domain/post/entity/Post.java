package com.example.demo.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {

    private final Long id;
    private final Long memberId;
    private final String contents;
    private Long likeCount;
    private final LocalDate createdDate;
    private final LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, Long likeCount, LocalDate createdDate, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        /*
            중간에 데이터를 추가 하는 경우
            1. 디폴드 값으로 -> 테이블 락. 24시간 서비스라면 어려움
            2. 별도의 마이그레이션 배치를 만들어서 조금씩 채워줌
            3. 조회 시점에 null이면 값을 채워주는 방법
         */
        this.likeCount = likeCount == null ? 0 : likeCount;
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }
}
