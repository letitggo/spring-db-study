package com.example.demo.domain.post.repository.jdbcRepository;

import com.example.demo.util.PageHelper;
import com.example.demo.domain.post.dto.DailyPostCount;
import com.example.demo.domain.post.dto.DailyPostCountRequest;
import com.example.demo.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final String TABLE = "post";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<DailyPostCount> DAILY_POST_COUNT_ROW_MAPPER = (rs, rowNum) ->
        new DailyPostCount(
                rs.getLong("memberId"),
                rs.getObject("createdDate", LocalDate.class),
                rs.getLong("count")
        );
    private final RowMapper<Post> ROW_MAPPER = (rs, rowNum) ->
            Post.builder()
                    .id(rs.getLong("id"))
                    .memberId(rs.getLong("member_id"))
                    .contents(rs.getString("contents"))
                    .likeCount(rs.getLong("like_count"))
                    .version(rs.getLong("version"))
                    .createdDate(rs.getObject("created_date", LocalDate.class))
                    .createdAt(rs.getObject("created_at", LocalDateTime.class))
                    .build();

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        // 아래 쿼리는 데이터가 많아지면 성능이 떨어지는 문제가 있음
        String sql = String.format("""
                    SELECT memberId, createdDate, count(id) as count
                    FROM %s
                    WHERE memberId = :memberId AND createdDate BETWEEN :firstDate AND :lastDate
                    GROUP BY memberId, createdDate;
                """, TABLE);
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_ROW_MAPPER);
    }

    public Optional<Post> findById(Long postId, Boolean requiredLock) {
        String sql = String.format("""
                    SELECT *
                    FROM %s
                    WHERE id = :postId
                """, TABLE);
        if (requiredLock) {
            sql += "FOR UPDATE";
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postId", postId);
        Post nullablePost = namedParameterJdbcTemplate.queryForObject(sql, params, ROW_MAPPER);
        return Optional.ofNullable(nullablePost);
    }

    public Page<Post> findAllByMemberId(Long memberId, Pageable pageable) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        String sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY %s
                LIMIT :size
                OFFSET :offset
                """, TABLE, PageHelper.orderBy(pageable.getSort()));
        List<Post> posts = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
        return new PageImpl<>(posts, pageable, getCount(memberId));
    }

    private Long getCount(Long memberId) {
        String sql = String.format("""
                SELECT count(id) as count
                FROM %s
                WHERE memberId = :memberId
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public List<Post> findAllByMemberIdAndOrderByIdDesc(Long memberId, int size) {
        String sql = String.format("""
                    SELECT *
                    FROM %s
                    WHERE member_id = :memberId
                    ORDER BY id desc
                    LIMIT :size
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public List<Post> findAllByInMemberIdAndOrderByIdDesc(List<Long> memberIds, int size) {
        if (memberIds.isEmpty()) {
            return List.of();
        }

        String sql = String.format("""
                    SELECT *
                    FROM %s
                    WHERE memberId in(:memberIds)
                    ORDER BY id desc
                    LIMIT :size
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberIds", memberIds)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public List<Post> findByAllByInId(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        String sql = String.format("""
                    SELECT *
                    FROM %s
                    WHERE id in (:ids)
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("ids", ids);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public List<Post> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, int size) {
        String sql = String.format("""
                    SELECT *
                    FROM %s
                    WHERE memberId = :memberId and id < :id
                    ORDER BY id desc
                    LIMIT :size
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("id", id)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public List<Post> findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(Long id, List<Long> memberIds, int size) {
        if (memberIds.isEmpty()) {
            return List.of();
        }

        String sql = String.format("""
                    SELECT *
                    FROM %s
                    WHERE memberId in(:memberIds) and id < :id
                    ORDER BY id desc
                    LIMIT :size
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberIds", memberIds)
                .addValue("id", id)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        }
        // TODO update
        return update(post);
    }


    public void bulkInsert (List < Post > posts) {
        String sql = String.format("""
                    INSERT INTO `%s` (memberId, contents, createdDate, createdAt)
                    VALUES (:memberId, :contents, :createdDate, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private Post insert (Post post){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(post);

        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

    private Post update (Post post){
        String sql = String.format("""
                UPDATE %s SET
                    memberId = :memberId,
                    contents = :contents,
                    likeCount = :likeCount,
                    createdDate = :createdDate,
                    createdAt = :createdAt,
                    version = :version + 1
                WHERE id = :id AND version = :version
                """, TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        int updated = namedParameterJdbcTemplate.update(sql, params);
        if (updated == 0) {
            throw new RuntimeException("갱신 실패");
        }
        return post;
    }
}
