package com.example.demo.application.controller;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.MemberNicknameHistoryDto;
import com.example.demo.domain.member.dto.RegisterMemberCommand;
import com.example.demo.domain.member.dto.UpdateFcmTokenCommand;
import com.example.demo.domain.member.service.MemberReadService;
import com.example.demo.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        return memberWriteService.register(command);
    }

    @GetMapping("/{memberId}")
    public MemberDto getMember(@PathVariable Long memberId) {
        return memberReadService.getMember(memberId);
    }

    @PostMapping("/{memberId}/name")
    public MemberDto changNickname(
            @PathVariable Long memberId,
            String nickname
    ) {
        memberWriteService.changeNickname(memberId, nickname);
        return memberReadService.getMember(memberId);
    }

    @GetMapping("/{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistories(memberId);
    }

    @PostMapping("/{memberId}/fcm-token")
    public ResponseEntity<Void> memberFcmTokenUpdate(
            @PathVariable Long memberId,
            @RequestBody UpdateFcmTokenCommand command
    ) {
        memberWriteService.updateToken(command, memberId);
        return ResponseEntity.ok().build();
    }
}
