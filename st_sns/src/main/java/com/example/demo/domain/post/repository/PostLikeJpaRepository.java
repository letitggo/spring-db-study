package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeJpaRepository extends JpaRepository<PostLike, Long> {

    Long countPostLikesByPostId(Long PostId);
}
