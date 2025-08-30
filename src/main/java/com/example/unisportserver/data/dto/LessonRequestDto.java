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

    @Schema(example = "축구", description = "레슨 종목")
    private String sport;

    @Schema(example = "초급 축구", description = "레슨명")
    private String title;

    @Schema(example = "초보자를 위한 축구 레슨", description = "레슨 설명")
    private String description;

    @Schema(example = "1", description = "레슨 난이도 1-5")
    private Integer level;

    @Schema(example = "1", description = "강사 userId")
    private Long instructorUserId;
    
    @Schema(example = "화정체육관")
    private String location;

    @Schema(example = "10", description = "레슨 정원")
    private Integer capacity;

    @Schema(example = "1", description = "몇 주마다 반복인지")
    private Integer intervalWeeks;

    @Schema(example = "2025-09-01", description = "레슨 시작 날짜, 이걸로 요일도 계산")
    private LocalDate StartDate;

    @Schema(example = "16", description = "수업 총 횟수")
    private Integer totalCount;

    @Schema(example = "14:00", description = "레슨 시작 시간")
    private LocalTime startTime;
    
    @Schema(example = "16:00", description = "레슨 종료 시간")
    private LocalTime endTime;
}
