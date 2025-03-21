package com.example.demo.domain.auth.client.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserResponse (
        Long id,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
){
}
