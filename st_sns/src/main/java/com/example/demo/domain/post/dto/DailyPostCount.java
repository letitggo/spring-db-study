package com.example.demo.domain.post.dto;

import java.time.LocalDate;

public record DailyPostCount(
        Long memberId,
        LocalDate createdDate,
        Long count
) {
}
