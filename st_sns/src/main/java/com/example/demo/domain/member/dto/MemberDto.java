package com.example.demo.domain.member.dto;

import com.example.demo.domain.member.entity.Member;

import java.time.LocalDate;

public record MemberDto(
        Long id,
        String email,
        String nickname,
        LocalDate birthday,
        String fcmToken
) {
    public static MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getBirthday(),
                member.getFcmToken()
        );
    }
}
