package com.example.demo.domain.auth.client.kakao.registration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth.kakao.url")
@Getter
@Setter
public class KakaoClientUrlRegistration {
    private String authCodeUrl;
    private String tokenUrl;
    private String userInfoUrl;
}
