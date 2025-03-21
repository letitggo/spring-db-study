package com.example.demo.domain.auth.client.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoAccount(
        @JsonProperty("email")
        String email,
        @JsonProperty("profile")
        KakaoProfile profile
) {

    public record KakaoProfile(
            @JsonProperty("nickname")
        String nickname
    ) {

    }
}
