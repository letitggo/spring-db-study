package com.example.demo.domain.auth.application;

import com.example.demo.domain.auth.client.AuthCodeRequestUrlRegistry;
import com.example.demo.domain.auth.client.MemberAuthClientRegistry;
import com.example.demo.domain.auth.domain.OauthType;
import com.example.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthCodeRequestUrlRegistry urlMapper;
    private final MemberAuthClientRegistry memberAuthClientRegistry;

    public String getAuthCodeRedirectUrl(OauthType oauthType) {
        return urlMapper.provide(oauthType);
    }

    public Member loginWithGenerateToken(OauthType oauthType, String code) {
        return memberAuthClientRegistry.fetch(oauthType, code);
    }
}
