package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.MemberNicknameHistoryDto;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.demo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return MemberDto.toDto(member);
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberNicknameHistoryDto::toDto)
                .toList();
    }
}
