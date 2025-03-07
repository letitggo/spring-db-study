package com.example.demo.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/*
    닉네임 변경 내역을 저장
    정규화를 하려고 하는 대상이 데이터의 최신성을 반드시 보장해야하는지 고려를 해야한다.
    ex) 이커머스에서 제조사의 식별자를 남긴다 가정 -> 제조사 이름이 바뀌게 된다면 주문 히스토리도 바뀐 제조사 이름으로 변경해야 하는가?
        비즈니스 정책에 따라 다르겠지만 모호한 것들이 생기기 마련, 최신성을 보장해야하는지가 제일 중요 (PM, 기획자등에게 요구사항을 계속해서 도출하자)
 */
@Getter
public class MemberNicknameHistory {
    private final Long id;
    private final Long memberId;
    private final String nickname;      // 과거에 데이터(히스토리)는 정규화의 대상이 아니다.
    private final LocalDateTime createdAt;

    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt;
    }
}