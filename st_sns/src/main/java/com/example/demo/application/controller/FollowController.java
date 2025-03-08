package com.example.demo.application.controller;

import com.example.demo.application.usecase.CreateFollowMemberUseCase;
import com.example.demo.application.usecase.GetFollowingMembersUseCase;
import com.example.demo.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final CreateFollowMemberUseCase createFollowMemberUsecase;
    private final GetFollowingMembersUseCase getFollowingMembersUseCase;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public void follow(
            @PathVariable Long fromMemberId,
            @PathVariable Long toMemberId
    ) {
        createFollowMemberUsecase.execute(fromMemberId, toMemberId);
    }

    @GetMapping("/members/{fromMemberId}")
    public List<MemberDto> getFollowings(@PathVariable Long fromMemberId) {
        return getFollowingMembersUseCase.execute(fromMemberId);
    }
}
