package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.entity.Timeline;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimelineJpaRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findAllByIdLessThanAndMemberIdOrderByIdDesc(Long id, Long memberId, Limit limit);
    List<Timeline> findAllByMemberIdOrderById(Long memberId, Limit limit);
}