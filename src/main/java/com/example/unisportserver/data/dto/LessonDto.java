package com.example.unisportserver.data.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {
    private String id;
    private String title;
    private String description;
    private String sport;
}

// TODO: DTO 분리 필요
