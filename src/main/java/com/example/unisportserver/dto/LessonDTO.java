package com.example.unisportserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
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
