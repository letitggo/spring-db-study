package com.example.demo.domain.member.repository.jpaRepository;

import com.example.demo.domain.member.entity.MemberNicknameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberNicknameHistoryJpaRepository extends JpaRepository<MemberNicknameHistory, Long> {

    List<MemberNicknameHistory> findAllByMemberId(Long memberId);
}
