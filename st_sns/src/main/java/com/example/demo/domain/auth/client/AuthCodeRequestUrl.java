package com.example.demo.domain.auth.client;

import com.example.demo.domain.auth.domain.OauthType;

public interface AuthCodeRequestUrl {

    OauthType supportType();

    String provide();
}
