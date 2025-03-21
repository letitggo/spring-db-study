package com.example.demo.domain.auth.client.google;


import com.example.demo.domain.auth.client.AuthCodeRequestUrl;
import com.example.demo.domain.auth.client.google.registration.GoogleClientRegistrationRepository;
import com.example.demo.domain.auth.domain.OauthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleAuthCodeRequestUrl implements AuthCodeRequestUrl {

    // TODO 구글 로그인도 구현하자
    private final GoogleClientRegistrationRepository registrationRepository;

    @Override
    public OauthType supportType() {
        return OauthType.GOOGLE;
    }

    @Override
    public String provide() {
//        return UriComponentsBuilder.fromUriString(registrationRepository.getUrl().getAuthCodeUrl())
//                .queryParam("client_id", registrationRepository.getClientId())
//                .queryParam("redirect_uri", registrationRepository.getRedirectUrl())
//                .queryParam("response_type", "code")
//                .toUriString();
        return null;
    }
}
