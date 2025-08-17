package com.example.unisportserver.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "레슨 Dto")
public class LessonDto {

    @Schema(description = "레슨 이름", example = "초급 축구")
    private String title;

    @Schema(description = "레슨 설명", example = "초보자를 위한 축구 수업")
    private String description;

    @Schema(description = "운동 종류", example = "축구")
    private String sport;
}

// TODO: DTO 분리 필요
