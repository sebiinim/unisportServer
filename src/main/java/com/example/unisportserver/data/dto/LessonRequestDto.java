package com.example.unisportserver.data.dto;

import com.example.unisportserver.data.enums.DayOfTheWeek;
import com.example.unisportserver.data.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class LessonRequestDto {

    @Schema(example = "축구")
    private String sport;

    @Schema(example = "초급 축구")
    private String title;

    @Schema(example = "초보자를 위한 축구 수업")
    private String description;

    @Schema(example = "1")
    private Integer level;

    @Schema(example = "화정체육관")
    private String location;

    @Schema(example = "10", description = "수업 정원")
    private Integer capacity;

    @Schema(example = "1", description = "매주인지 격주인지 bool, 1이면 매주, 0이면 격주")
    private Boolean isEveryWeek;

    @Schema(example = "FRIDAY", description = "수업 요일 enum, SUNDAY부터 SATURDAY까지 영어 대문자")
    private DayOfTheWeek dayOfTheWeek;

    @Schema(example = "1")
    private Long instructorUserId;

    @Schema(example = "2025-09-01")
    private LocalDate lessonDate;

    @Schema(example = "14:00")
    private LocalTime lessonTime;
}
