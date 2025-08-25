package com.example.unisportserver.data.entity;

import com.example.unisportserver.data.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "lesson")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sport;

    private String title;

    private String description;

    private Integer level;

    private String location;

    private Integer reserved_count;     // 예약 인원
    
    private Integer capacity;           // 수업 정원

    private LessonReservationStatus status;     // 정원 상태

    private Long instructorUserId;

    private LocalDate lessonDate;

    private LocalTime lessonTime;
}

enum LessonReservationStatus {
    AVAILABLE, FULL
}
