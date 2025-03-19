package com.example.demo.domain.member.service.impl;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.RegisterMemberCommand;
import com.example.demo.domain.member.dto.UpdateFcmTokenCommand;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.MemberNicknameHistory;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.repository.jpaRepository.MemberNicknameHistoryJpaRepository;
import com.example.demo.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberWriteServiceImpl implements MemberWriteService {

    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryJpaRepository memberNicknameHistoryJpaRepository;

    /*@Transactional
    public MemberDto register(RegisterMemberCommand command) {
        Member member = Member.builder()
                .nickname(command.nickname())
                .birthday(command.birthday())
                .email(command.email())
                .build();
        Member saved = MemberRepository.save(member);
        saveMemberNicknameHistory(saved);
        return MemberDto.toDto(saved);
    }*/

    @Transactional
    public MemberDto register(RegisterMemberCommand command) {
        Member member = Member.builder()
                .email(command.email())
                .nickname(command.nickname())
                .birthday(command.birthday())
                .createdAt(LocalDateTime.now())
                .build();
        Member saved = memberRepository.save(member);
        saveMemberNicknameHistory(saved);
        return MemberDto.toDto(saved);
    }

    @Transactional
    public void changeNickname(Long memberId, String nickname) {
        /*
            1. 회원의 이름은 변경
            2. 변경 내역을 저장
         */
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        MemberNicknameHistory history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .createdAt(LocalDateTime.now())
                .build();
        memberNicknameHistoryJpaRepository.save(history);
    }

    @Transactional
    public void updateToken(UpdateFcmTokenCommand command, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.updateFcmToken(command.token());
    }
}
