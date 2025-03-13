package com.example.demo.domain.post.repository.jdbcRepository;

import com.example.demo.domain.post.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {

    private final String TABLE = "PostLike";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<PostLike> ROW_MAPPER = (rs, rowNum) -> PostLike.builder()
            .id(rs.getLong("id"))
            .postId(rs.getLong("postId"))
            .memberId(rs.getLong("memberId"))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build();

    public Long count(Long postId) {
        String sql = String.format("""
                    SELECT count(id)
                    FROM %s
                    WHERE postId = :postId
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postId", postId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public PostLike save(PostLike postLike) {
        if (postLike.getId() == null) {
            return insert(postLike);
        }
        throw new UnsupportedOperationException("PostLike는 갱신을 지원하지 않습니다.");
    }

    public PostLike insert(PostLike postLike) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .usingGeneratedKeyColumns("id")
                .withTableName(TABLE);

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(postLike);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return PostLike.builder()
                .id(id)
                .postId(postLike.getPostId())
                .memberId(postLike.getMemberId())
                .createdAt(postLike.getCreatedAt())
                .build();
    }
}
