package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter @RequiredArgsConstructor
@Entity
@Table(name = "lesson_schedule")
public class LessonScheduleEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;

    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;
}
