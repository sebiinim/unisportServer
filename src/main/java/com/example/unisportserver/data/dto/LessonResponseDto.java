package com.example.unisportserver.data.dto;

import com.example.unisportserver.data.enums.DayOfTheWeek;
import com.example.unisportserver.data.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class LessonResponseDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "축구")
    private String sport;

    @Schema(example = "초급 축구")
    private String title;

    @Schema(example = "초보자를 위한 축구 수업")
    private String description;

    @Schema(example = "1")
    private Long instructorUserId;

    @Schema(example = "1")
    private Integer level;

    @Schema(example = "화정체육관")
    private String location;

    @Schema(example = "10", description = "수업 정원")
    private Integer capacity;

    @Schema(example = "eximageurl", description = "이미지 url")
    private String imagePath;

    private List<LessonScheduleResponseDto> schedules;
}
