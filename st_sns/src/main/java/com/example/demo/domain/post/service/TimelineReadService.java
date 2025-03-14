package com.example.demo.domain.post.service;

import com.example.demo.domain.post.entity.Timeline;
import com.example.demo.domain.post.repository.TimelineJpaRepository;
import com.example.demo.util.CursorRequest;
import com.example.demo.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineReadService {

    private final TimelineJpaRepository timelineJpaRepository;

    public PageCursor<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        List<Timeline> timelines = findAllBy(memberId, cursorRequest);
        long nextKey = timelines.stream()
                .mapToLong(Timeline::getId)
                .min().orElse(CursorRequest.NONE_KEY);
        return new PageCursor<>(cursorRequest.next(nextKey), timelines);
    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return timelineJpaRepository.findAllByIdLessThanAndMemberIdOrderByIdDesc(cursorRequest.key(), memberId, Limit.of(cursorRequest.size()));
        }
        return timelineJpaRepository.findAllByMemberIdOrderById(memberId, Limit.of(cursorRequest.size()));
    }
}
