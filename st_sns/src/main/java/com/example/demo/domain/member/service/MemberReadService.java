package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.MemberNicknameHistoryDto;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberJpaRepository;
import com.example.demo.domain.member.repository.MemberNicknameHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberNicknameHistoryJpaRepository memberNicknameHistoryJpaRepository;

    public MemberDto getMember(Long id) {
        Member member = memberJpaRepository.findById(id).orElseThrow();
        return MemberDto.toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        List<Member> members = memberJpaRepository.findAllByIdIn(ids);
        return members
                .stream()
                .map(MemberDto::toDto)
                .toList();
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryJpaRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberNicknameHistoryDto::toDto)
                .toList();
    }
}
