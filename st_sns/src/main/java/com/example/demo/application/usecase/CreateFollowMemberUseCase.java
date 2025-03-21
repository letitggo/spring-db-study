package com.example.demo.application.usecase;

import com.example.demo.domain.follow.service.FollowWriteService;
import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.service.MemberReadService;
import com.example.demo.domain.notification.NotificationChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    도메인간 흐름을 제어하는 계층
    경계를 나누고 경계간의 통신을 담당

    팔로우 기능 하나만을 담당하는 usecase를 만들기 위해 클래스명을 동사로 만듦
    모든 유스케이스는 execute로 동일하게

    + 클래스명에서 어떤 요구사항을 만족하는 usecase인지 명확히 이름을 짓도록 해보자
    + 다릏게 해도 됨.
 */
@Service
@RequiredArgsConstructor
public class CreateFollowMemberUseCase {

    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;
    private final NotificationChannel notificationChannel;

    @Transactional
    public void execute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력 받은 memberId로 회원 조회
            2. FollowWriteService.create()
         */
        MemberDto fromMember = memberReadService.getMember(fromMemberId);
        MemberDto toMember = memberReadService.getMember(toMemberId);
        notificationChannel.send(toMember, "팔로우 알림", fromMember.nickname() + "님이 회원님을 팔로우했어요!");

        followWriteService.create(fromMember, toMember);
    }
}
