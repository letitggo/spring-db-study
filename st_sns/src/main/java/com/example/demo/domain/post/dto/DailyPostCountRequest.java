package com.example.demo.domain.post.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DailyPostCountRequest(
        Long memberId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        LocalDate firstDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        LocalDate lastDate
) {
}
