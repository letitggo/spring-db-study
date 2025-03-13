package com.example.demo.domain.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String contents;
    private Long likeCount;
    @Version // 낙관적 락 버전 정보
    private Long version;
    private LocalDate createdDate;
    private LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, Long likeCount, Long version, LocalDate createdDate, LocalDateTime createdAt) {
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
        this.version = version == null ? 0 : version;
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }
}
