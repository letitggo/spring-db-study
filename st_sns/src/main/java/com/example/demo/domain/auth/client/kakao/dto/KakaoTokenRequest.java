package com.example.demo.domain.auth.client.kakao.dto;

import com.example.demo.domain.auth.client.kakao.registration.KakaoClientRegistrationRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record KakaoTokenRequest(
        String grantType,
        String clientId,
        String redirectUri,
        String code
) {

    public static KakaoTokenRequest of(
        KakaoClientRegistrationRepository registrationRepository,
        String code
    ) {
        return new KakaoTokenRequest(
                "authorization_code",
                registrationRepository.getClientId(),
                registrationRepository.getRedirectUrl(),
                code
        );
    }

    public MultiValueMap<String, Object> toMultivalueMap() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);
        map.add("client_id", clientId);
        map.add("redirect_uri", redirectUri);
        map.add("code", code);
        return map;
    }
}
