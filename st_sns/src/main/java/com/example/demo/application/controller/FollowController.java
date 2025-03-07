package com.example.demo.application.controller;

import com.example.demo.application.usecase.CreateFollowMemberUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final CreateFollowMemberUsecase createFollowMemberUsecase;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public void follow(
            @PathVariable Long fromMemberId,
            @PathVariable Long toMemberId
    ) {
        createFollowMemberUsecase.execute(fromMemberId, toMemberId);
    }

}
