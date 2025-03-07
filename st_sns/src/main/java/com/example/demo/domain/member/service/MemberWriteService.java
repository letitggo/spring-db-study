package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.RegisterMemberCommand;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

    public MemberDto register(RegisterMemberCommand command) {
        Member member = Member.builder()
                .nickname(command.nickname())
                .birthday(command.birthday())
                .email(command.email())
                .build();

        return MemberDto.toDto(memberRepository.save(member));
    }

    public void changeNickname(Long memberId, String nickname) {
        /*
            1. 회원의 이름은 변경
            2. 변경 내역을 저장
         */

        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);
        // TODO 변경 내역 히스토리를 저장
    }
}
