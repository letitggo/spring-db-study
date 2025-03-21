package com.example.demo.domain.auth.client;

import com.example.demo.domain.auth.domain.OauthType;
import com.example.demo.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MemberAuthClientMapper {

    private Map<OauthType, MemberAuthClient> mapping;

    public MemberAuthClientMapper(Set<MemberAuthClient> clients) {
        this.mapping = clients.stream()
                .collect(Collectors.toUnmodifiableMap(
                        MemberAuthClient::supportType,  // 구현체에게서 key 추출
                        // value 추출 (MemberAuthClient 구현체 자신을 value로)
                        // 입력 객체 그대로 출력 T -> T 따라서 Bean으로 등록된 객체 그냥 넣음
                        Function.identity()
                ));
    }

    public Member fetch(OauthType oauthType, String code) {
        Member member = Optional.ofNullable(mapping.get(oauthType))
                .orElseThrow(() -> new RuntimeException("지원하는 소셜 로그인 아닙니다~"))
                .fetch(code);
        return member;
    }
}
