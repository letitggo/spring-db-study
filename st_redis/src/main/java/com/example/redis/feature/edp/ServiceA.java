package com.example.redis.feature.edp;

import com.example.redis.feature.edp.dto.SomethingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceA {

    private final ApplicationEventPublisher eventPublisher;

    public void doSomething(SomethingDto something) {
        System.out.println("ServiceA : " + something.email());
        System.out.println("ServiceA : " + something.something());
        eventPublisher.publishEvent(new EventA(something));
    }
}
