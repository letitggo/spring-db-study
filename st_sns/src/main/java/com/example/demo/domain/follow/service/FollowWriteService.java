package com.example.demo.domain.follow.service;

import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.repository.FollowJpaRepository;
import com.example.demo.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class FollowWriteService {

    private final FollowJpaRepository followJpaRepository;

    /*
        1. Member Entity를 매개변수로 넘기는 경우 강결합 발생
        2. Member의 id를 매개변수로 넘기는 경우 이 Member가 존재하는지 확인하기 위해서는 결국 강결합이 발생
     */
    public void create(MemberDto fromMember, MemberDto toMember) {
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일합니다.");

        Follow follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();
        followJpaRepository.save(follow);
    }
}
