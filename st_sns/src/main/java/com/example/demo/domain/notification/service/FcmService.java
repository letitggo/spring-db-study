package com.example.demo.domain.notification.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    public ApiFuture<String> sendMessage(String token, String title, String body) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        Message message = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder().setTitle(title).setBody(body).build()
                ).build();

        return FirebaseMessaging.getInstance().sendAsync(message);
    }
}
