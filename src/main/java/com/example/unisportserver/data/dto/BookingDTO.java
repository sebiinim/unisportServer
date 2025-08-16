package com.example.unisportserver.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String id;
    private LessonDto lesson;              // 수업 정보
    private UserDTO student;               // 예약한 학생 정보
    private LessonScheduleDTO schedule;    // 예약된 시간표
    private String status;                 // confirmed, cancelled, completed
    private LocalDateTime createdAt;
}
