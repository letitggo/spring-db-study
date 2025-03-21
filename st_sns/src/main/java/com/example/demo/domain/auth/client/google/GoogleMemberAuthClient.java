package com.example.demo.domain.auth.client.google;

import com.example.demo.domain.auth.client.MemberAuthClient;
import com.example.demo.domain.auth.domain.OauthType;
import com.example.demo.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class GoogleMemberAuthClient implements MemberAuthClient {

    // TODO
    @Override
    public OauthType supportType() {
        return OauthType.GOOGLE;
    }

    @Override
    public Member fetch(String code) {
        return null;
    }
}
