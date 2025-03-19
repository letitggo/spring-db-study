package com.example.redis.feature.edp;

import com.example.redis.feature.edp.dto.SomethingDto;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    @EventListener
    public void handelEventA(EventA eventA) {
        SomethingDto source = (SomethingDto) eventA.getSource();
        System.out.println("ServiceB handle start.. : " + source.something());
        System.out.println("ServiceB handle start.. : " + eventA.getSomethingDto().email());
    }
}
