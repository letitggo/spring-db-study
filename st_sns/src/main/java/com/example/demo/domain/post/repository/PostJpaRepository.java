package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.dto.DailyPostCount;
import com.example.demo.domain.post.entity.Post;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Query("""
        select new com.example.demo.domain.post.dto.DailyPostCount(
            p.memberId, p.createdDate, count(p.id)
        )
        from Post p
        where p.memberId = :memberId
        and p.createdDate between :firstDate and :lastDate
        group by p.memberId, p.createdDate
    """)
    List<DailyPostCount> groupByCreatedDate(
            @Param("memberId") Long memberId,
            @Param("firstDate") LocalDate firstDate,
            @Param("lastDate") LocalDate lastDate
    );

    Page<Post> findAllByMemberId(Long memberId, Pageable pageable);
    List<Post> findAllByIdIn(List<Long> ids);

    // cursor 기반 페이지네이션
    List<Post> findAllByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long id, Limit limit);
    List<Post> findAllByMemberIdOrderByIdDesc(Long memberId, Limit limit);

    List<Post> findAllByMemberIdInAndIdLessThanOrderByIdDesc(List<Long> memberId, Long id, Limit limit);
    List<Post> findAllByMemberIdInOrderByIdDesc(List<Long> memberId, Limit limit);

    /*
        비관적 락
        PESSIMISTIC_READ : 다른 트랜잭션에서 쓰기, 삭제 방지(DB 대부분에서 동작 X -> READ로 동작)
        PESSIMISTIC_WRITE : 다른 트랜잭션에서 읽기, 쓰기, 삭제 방지
        PESSIMISTIC_FORCE+INCREMENT : WRITE와 유사하게 동작, 비관적 락이지만 버전 정보를 강제로 증가
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdWithLock(Long id);

    /*
        낙관적 락
        NONE : @Version이 있으면 기본으로 사용되는 옵션, 수정할 때 버전 증가
        OPTIMISTIC : 조회만 해도 버전 증가
        OPTIMISTIC_FORCE_INCREMENT : 낙관적 락을 사용하면서 버전 정보를 강제로 증가
     */
    @Lock(LockModeType.NONE)
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdWithOptimisticLock(Long id);
}

