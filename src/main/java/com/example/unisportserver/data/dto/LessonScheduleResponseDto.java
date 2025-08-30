package com.example.unisportserver.data.dto;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonScheduleResponseDto {
    private Long id;
    private Long lessonId;
    private LocalDate date;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer capacity;    // effective(override or lesson)
    private Integer reservedCount;
    private String location;     // effective(override or lesson)
}