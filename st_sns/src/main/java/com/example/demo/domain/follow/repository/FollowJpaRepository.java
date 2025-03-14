package com.example.demo.domain.follow.repository;

import com.example.demo.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFromMemberId(Long fromMemberId);
    List<Follow> findAllByToMemberId(Long toMemberId);
}