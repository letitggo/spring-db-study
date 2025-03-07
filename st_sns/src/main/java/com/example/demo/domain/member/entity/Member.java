package com.example.demo.domain.member.entity;


import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private final Long id;
    private String nickname;    // 닉네임은 변경 가능
    private final String email;
    private final LocalDate birthday;
    private final LocalDateTime createdAt;

    private final static Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);

        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void changeNickname(String other) {
        Objects.requireNonNull(other);
        validateNickname(other);
        nickname = other;
    }

    // custom Exception을 넣어도 되지만 빠르게 진행하기 위해,,
    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }

}
