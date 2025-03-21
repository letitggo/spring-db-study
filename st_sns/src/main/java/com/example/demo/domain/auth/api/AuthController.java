package com.example.demo.domain.auth.api;

import com.example.demo.domain.auth.application.AuthService;
import com.example.demo.domain.auth.domain.OauthType;
import com.example.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/api/auth/{oauthType}")
    public String redirectAuthRequestUrl(@PathVariable OauthType oauthType) {
        String url = authService.getAuthCodeRedirectUrl(oauthType);
        return url;
    }

    @PostMapping("/api/auth/login/{oauthType}")
    public Member login(
        @PathVariable OauthType oauthType,
        @RequestParam String code
    ) {
        // dto로 바꿔야 하긴 함
        return authService.loginWithGenerateToken(oauthType, code);
    }
}
















