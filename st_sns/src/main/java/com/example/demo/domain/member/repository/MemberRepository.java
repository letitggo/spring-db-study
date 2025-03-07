package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE = "member";

    public Optional<Member> findById(Long id) {
        /*
            select * from member
            where id = :id
         */
        String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        // BeanPropertyRowMapper를 사용해도 되지만 setter를 열어줘야 하기 때문에 NO
        RowMapper<Member> rowMapper = (rs, rowNum) -> Member
                .builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .nickname(rs.getString("nickname"))
                .birthday(rs.getObject("birthday", LocalDate.class))
                .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                .build();

        Member member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(member);

    }

    public Member save(Member member) {
        /*
            TODO
            member id를 보고 갱신 또는 삽입을 정한다
            반환값은 id를 담아서 반환
         */
        if (member.getId() == null) {
            return insert(member);
        }

        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);

        Long id = simpleJdbcInsert.executeAndReturnKey(param).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member) {
        // TODO: implement
        return member;
    }
}
