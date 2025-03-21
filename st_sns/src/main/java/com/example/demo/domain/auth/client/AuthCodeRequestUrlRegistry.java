package com.example.demo.domain.auth.client;

import com.example.demo.domain.auth.domain.OauthType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthCodeRequestUrlRegistry {

    private final Map<OauthType, AuthCodeRequestUrl> mapping;

    public AuthCodeRequestUrlRegistry(Set<AuthCodeRequestUrl> providers) {
        this.mapping = providers.stream()
                // mapping 채워주기
                // 1. 빈 객체 자신의 OauthType을 key로
                // 2. 자기 자신(빈)을 value로 저장
                .collect(Collectors.toUnmodifiableMap(AuthCodeRequestUrl::supportType, Function.identity()));
    }
    // 3. 이후 oauthType으로 알맞는 빈 객체 찾아서 provide()호출
    public String provide(OauthType oauthType) {
        String url = Optional.ofNullable(mapping.get(oauthType))
                .orElseThrow(() -> new RuntimeException("지원하는 프로바이더 아님."))
                .provide();
        return url;
    }
}
