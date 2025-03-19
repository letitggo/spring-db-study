package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.MemberNicknameHistoryDto;

import java.util.List;

public interface MemberReadService{

    MemberDto getMember(Long id);
    List<MemberDto> getMembers(List<Long> ids);
    List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId);
}
