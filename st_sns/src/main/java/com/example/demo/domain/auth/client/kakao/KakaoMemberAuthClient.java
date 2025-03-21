package com.example.demo.domain.auth.client.kakao;

import com.example.demo.domain.auth.client.MemberAuthClient;
import com.example.demo.domain.auth.client.kakao.dto.KakaoTokenRequest;
import com.example.demo.domain.auth.client.kakao.dto.KakaoTokenResponse;
import com.example.demo.domain.auth.client.kakao.dto.KakaoUserResponse;
import com.example.demo.domain.auth.client.kakao.registration.KakaoClientRegistrationRepository;
import com.example.demo.domain.auth.domain.OauthType;
import com.example.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class KakaoMemberAuthClient implements MemberAuthClient {

    private final RestClient restClient;
    private final KakaoClientRegistrationRepository registrationRepository;

    @Override
    public OauthType supportType() {
        return OauthType.KAKAO;
    }

    @Override
    public Member fetch(String code) {
        KakaoTokenResponse tokenResponse = fetchAccessToken(code);
        KakaoUserResponse userResponse = restClient.post()
                .uri(registrationRepository.getUrl().getUserInfoUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.accessToken())
                .retrieve()
                .body(KakaoUserResponse.class);
        return Member.builder()
                .email(userResponse.kakaoAccount().email())
                .nickname(userResponse.kakaoAccount().profile().nickname())
                // ㅋ..
                .birthday(LocalDate.of(2001, 01, 01))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private KakaoTokenResponse fetchAccessToken(String code) {
        KakaoTokenRequest request = KakaoTokenRequest.of(registrationRepository, code);
        KakaoTokenResponse response = restClient.post()
                .uri(registrationRepository.getUrl().getTokenUrl())
                .body(request.toMultivalueMap())
                .retrieve()
                .body(KakaoTokenResponse.class);
        return response;
    }
}
