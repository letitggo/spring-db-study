package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    List<Member> findAllByIdIn(List<Long> ids);
    Optional<Member> findById(Long id);
    Member save(Member member);
}
