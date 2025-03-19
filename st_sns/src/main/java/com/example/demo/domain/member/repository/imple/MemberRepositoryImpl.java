package com.example.demo.domain.member.repository.imple;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.repository.jpaRepository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    // 어떤 db 접근 기술을 사용할지에따라 갈아끼우기만 하면 된다.
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public List<Member> findAllByIdIn(List<Long> ids) {
        return memberJpaRepository.findAllByIdIn(ids);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }
}
