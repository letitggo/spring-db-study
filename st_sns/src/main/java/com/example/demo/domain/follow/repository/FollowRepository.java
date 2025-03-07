package com.example.demo.domain.follow.repository;

import com.example.demo.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private static final String TABLE = "follow";
    private final JdbcTemplate jdbcTemplate;

    public Follow save(Follow follow) {
        if (follow.getId() == null)
            return insert(follow);

        throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다.");
    }

    private Follow insert(Follow follow) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(follow);
        long id = simpleJdbcInsert.executeAndReturnKey(param).longValue();

        return Follow.builder()
                .id(id)
                .fromMemberId(follow.getFromMemberId())
                .toMemberId(follow.getToMemberId())
                .createdAt(follow.getCreatedAt())
                .build();
    }
}
