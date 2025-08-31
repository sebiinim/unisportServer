package com.example.unisportserver.data.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class LessonWithScheduleResponseDto {
    private Long id;
    private String sport;
    private String title;
    private String description;
    private Integer level;
    private String location;
    private String image;
    private Long instructorUserId;

    private LocalDate lessondate;
    private LocalTime lessontime;
}
