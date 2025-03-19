package com.example.demo.domain.member.service.impl;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.MemberNicknameHistoryDto;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.repository.jpaRepository.MemberNicknameHistoryJpaRepository;
import com.example.demo.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadServiceImpl implements MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryJpaRepository memberNicknameHistoryJpaRepository;

    @Override
    public MemberDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return MemberDto.toDto(member);
    }

    @Override
    public List<MemberDto> getMembers(List<Long> ids) {
        List<Member> members = memberRepository.findAllByIdIn(ids);
        return members
                .stream()
                .map(MemberDto::toDto)
                .toList();
    }

    @Override
    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryJpaRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberNicknameHistoryDto::toDto)
                .toList();
    }
}
