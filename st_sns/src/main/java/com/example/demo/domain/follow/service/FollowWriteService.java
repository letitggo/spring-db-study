package com.example.demo.domain.follow.service;

import com.example.demo.domain.member.dto.MemberDto;
import org.springframework.stereotype.Service;

@Service
public class FollowWriteService {

    /*
        1. Member Entity를 매개변수로 넘기는 경우 강결합 발생
        2. Member의 id를 매개변수로 넘기는 경우 이 Member가 존재하는지 확인하기 위해서는 결국 강결합이 발생
     */
    public void create(MemberDto fromMember, MemberDto toMember) {
        /*
            from, to 회원 정보를 받아서 저장할텐데...
            추후 MSA 도입등의 경우를 고려하여 결합도를 최대한 낮춰야한다..
            from <-> to validate
         */
    }
}
