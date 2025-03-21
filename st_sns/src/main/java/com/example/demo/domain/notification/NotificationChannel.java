package com.example.demo.domain.notification;

import com.example.demo.domain.member.dto.MemberDto;

public interface NotificationChannel {

    public void send(MemberDto memberDto, String title, String body);
}
