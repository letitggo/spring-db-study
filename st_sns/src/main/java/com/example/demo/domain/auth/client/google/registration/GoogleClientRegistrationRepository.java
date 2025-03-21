package com.example.demo.domain.auth.client.google.registration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth.google")
@Getter
@Setter
/*
    VO인데 Setter를.... 이걸 어케 해결함? 필드마다 전부 @Value(${..}) 붙이긴 싫은데
 */
public class GoogleClientRegistrationRepository {
    private String clientId;
    private String redirectUrl;
    private GoogleClientUrlRegistration url;
}
