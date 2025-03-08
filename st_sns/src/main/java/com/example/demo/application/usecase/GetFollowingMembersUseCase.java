package com.example.demo.application.usecase;

import com.example.demo.domain.follow.dto.FollowDto;
import com.example.demo.domain.follow.service.FollowReadService;
import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMembersUseCase {
    private final FollowReadService followReadService;
    private final MemberReadService memberReadService;

    public List<MemberDto> execute(Long fromMemberId) {
        List<FollowDto> followings = followReadService.getFollowings(fromMemberId);
        List<Long> ids = followings
                .stream()
                .map(FollowDto::toMemberId)
                .toList();
        return memberReadService.getMembers(ids);
    }
}
