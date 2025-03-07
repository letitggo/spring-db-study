package com.example.demo.domain.controller;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.RegisterMemberCommand;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberReadService;
import com.example.demo.domain.member.service.MemberWriteService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        return memberWriteService.register(command);
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return memberReadService.getMember(id);
    }
}
