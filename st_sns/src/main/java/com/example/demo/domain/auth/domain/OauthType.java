package com.example.demo.domain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthType {
    GOOGLE,
    KAKAO;
}
