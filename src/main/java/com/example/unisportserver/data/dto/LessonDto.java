package com.example.unisportserver.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class LessonDto {
    
    // TODO: 수업에 사진을 추가해봅시다
    // 배포가 다시 되면 좋겠다

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

    private MultipartFile image;

    @Schema(example = "1")
    private Long instructorUserId;

    @Schema(example = "2025-08-18")
    private LocalDate lessonDate;

    @Schema(example = "02:00")
    private LocalTime lessonTime;
}
