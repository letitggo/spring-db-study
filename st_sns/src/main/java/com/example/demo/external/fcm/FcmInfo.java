package com.example.demo.external.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class FcmInfo {

//    @Value("${fcm.certification}")
//    private String fcmCertification;

    /*@PostConstruct
    public void init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(
                                GoogleCredentials.fromStream(
                                        new ByteArrayInputStream(
                                                fcmCertification.getBytes(
                                                        StandardCharsets.UTF_8))))
                        .build();

                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            log.info("Fcm initializing Exception : {}", e.getMessage());
        }
    }*/

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("./src/main/resources/firebase-service-account.json");
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
