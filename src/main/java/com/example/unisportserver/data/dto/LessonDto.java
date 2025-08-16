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
    private UserDTO instructor; // User 객체 참조
    private double price;
    private int duration;       // 단위: 분
    private int maxStudents;
    private String location;
    private List<LessonScheduleDTO> schedule;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
