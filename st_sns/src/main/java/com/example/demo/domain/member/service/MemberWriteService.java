package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.RegisterMemberCommand;
import com.example.demo.domain.member.dto.UpdateFcmTokenCommand;

public interface MemberWriteService {

    MemberDto register(RegisterMemberCommand command);
    void changeNickname(Long memberId, String nickname);
    void updateToken(UpdateFcmTokenCommand command, Long memberId);
}
