package com.example.demo.domain.auth.client.kakao;


import com.example.demo.domain.auth.client.AuthCodeRequestUrl;
import com.example.demo.domain.auth.client.kakao.registration.KakaoClientRegistrationRepository;
import com.example.demo.domain.auth.domain.OauthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeRequestUrl implements AuthCodeRequestUrl {

    private final KakaoClientRegistrationRepository registrationRepository;

    @Override
    public OauthType supportType() {
        return OauthType.KAKAO;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder.fromUriString(registrationRepository.getUrl().getAuthCodeUrl())
                .queryParam("client_id", registrationRepository.getClientId())
                .queryParam("redirect_uri", registrationRepository.getRedirectUrl())
                .queryParam("response_type", "code")
                .toUriString();
    }
}
