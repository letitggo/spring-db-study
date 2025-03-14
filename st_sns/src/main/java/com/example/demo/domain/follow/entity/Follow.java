package com.example.demo.domain.follow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
    포인트 1
    팔로워들의 정보(nickname)들이 항상 따라 올 것이기 때문에,
    1-1. 이런 정보들을 Follow 테이블에 넣어야할지
    1-2. 아니면 join을 할지
            -> join은 최대한 미루는 것이 좋다. 유연성있는 시스템이 되기 힘듦.
               리팩토링도 힘들고 성능 문제를 해결하기 쉽지 않다.
               + 결합도가 높아짐(member 쪽의 repository 레이어까지 진입)
    1-3. 아니면 식별자(id)만 가지고 있다가 조회를 할지

    포인트 2
    데이터 최신성을 보장해야하는가 - O
 */
@Getter
@Entity
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fromMemberId;
    private Long toMemberId;
    private LocalDateTime createdAt;

    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.fromMemberId = fromMemberId;
        this.toMemberId = toMemberId;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

}
