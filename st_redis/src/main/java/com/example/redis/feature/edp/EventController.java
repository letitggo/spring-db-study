package com.example.redis.feature.edp;

import com.example.redis.feature.edp.dto.SomethingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final ServiceA serviceA;

    // 이러한 이벤트 큐를 카프카같은 메시지 시스템을 사용한다면 멀티 서버에서도 동작이 가능
    // = Event-Driven-Architecture
    @PostMapping("/event")
    public String api(@RequestBody SomethingDto something) {
        serviceA.doSomething(something);
        return "ok";
    }
}
