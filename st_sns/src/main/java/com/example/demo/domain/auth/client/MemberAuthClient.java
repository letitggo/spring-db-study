package com.example.demo.domain.auth.client;

import com.example.demo.domain.auth.domain.OauthType;
import com.example.demo.domain.member.entity.Member;

public interface MemberAuthClient {

    OauthType supportType();

    Member fetch(String code);
}
