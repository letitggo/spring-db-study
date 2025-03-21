package com.example.demo.domain.notification.impl;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.notification.NotificationChannel;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmNotificationChannel implements NotificationChannel {

    @Override
    public void send(MemberDto memberDto, String title, String body) {
        String notificationToken = memberDto.notificationToken();

        if (notificationToken == null || notificationToken.isEmpty()) {
            throw new RuntimeException("notification token이 존재하지 않습니다.");
        }
        Message message = Message.builder()
                .setToken(notificationToken)
                .setNotification(
                        Notification.builder().setTitle(title).setBody(body).build()
                ).build();
        FirebaseMessaging.getInstance().sendAsync(message);
    }
}
