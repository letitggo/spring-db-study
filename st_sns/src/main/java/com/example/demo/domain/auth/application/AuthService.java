package com.example.demo.domain.auth.application;

import com.example.demo.domain.auth.client.AuthCodeRequestUrlMapper;
import com.example.demo.domain.auth.client.MemberAuthClientMapper;
import com.example.demo.domain.auth.domain.OauthType;
import com.example.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthCodeRequestUrlMapper urlMapper;
    private final MemberAuthClientMapper memberAuthClientMapper;

    public String getAuthCodeRedirectUrl(OauthType oauthType) {
        return urlMapper.provide(oauthType);
    }

    public Member loginWithGenerateToken(OauthType oauthType, String code) {
        return memberAuthClientMapper.fetch(oauthType, code);
    }
}
