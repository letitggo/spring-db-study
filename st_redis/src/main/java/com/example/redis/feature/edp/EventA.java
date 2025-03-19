package com.example.redis.feature.edp;

import com.example.redis.feature.edp.dto.SomethingDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventA extends ApplicationEvent {
    private final SomethingDto somethingDto;
    public EventA(SomethingDto something) {
        super(something);
        this.somethingDto = something;
    }
}
