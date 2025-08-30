package com.example.unisportserver.data.dto;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.enums.DayOfTheWeek;
import com.example.unisportserver.data.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class LessonDto {
    
    // TODO: 수업에 사진을 추가해봅시다
    // TODO: 정원과 예약인원을 추가해봅시다

    @Schema(example = "null")
    private Long id;

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

    @Schema(example = "0", description = "예약 인원")
    private Integer reserved_count;

    @Schema(example = "AVAILABLE", description = "enum(AVAILABLE | FULL)")
    private ReservationStatus reservation_status;

    @Schema(example = "eximageurl", description = "이미지 url")
    private String imagePath;

    @Schema(example = "1", description = "매주인지 격주인지 bool, 1이면 매주, 0이면 격주")
    private Boolean isEveryWeek;

    @Schema(example = "FRIDAY", description = "요일, SUNDAY부터 SATURDAY까지 영어 대문자")
    private DayOfTheWeek dayOfTheWeek;

    @Schema(example = "1")
    private Long instructorUserId;

    @Schema(example = "2025-09-01")
    private LocalDate lessonDate;

    @Schema(example = "14:00")
    private LocalTime lessonTime;
}
