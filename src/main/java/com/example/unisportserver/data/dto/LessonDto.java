package com.example.unisportserver.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {

    @Schema(example = "축구")
    private String sport;

    @Schema(example = "초급 축구")
    private String title;

    @Schema(example = "초보자를 위한 축구 수업")
    private String description;

    @Schema(example = "1")
    private String level;

    @Schema(example = "화정체육관")
    private String location;

    @Schema(example = "1")
    private String instructorUserId;

    @Schema(example = "2025-08-18")
    private LocalDate lessonDate;

    @Schema(example = "02:00")
    private LocalTime lessonTime;
}
