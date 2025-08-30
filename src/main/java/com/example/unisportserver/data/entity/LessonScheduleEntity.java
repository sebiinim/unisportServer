package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "lesson_schedule",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_lesson_schedule_unique", columnNames = {"lesson_id", "start_date", "start_time"})
        },
        indexes = {
                @Index(name = "ix_schedule_lesson", columnList = "lesson_id"),
                @Index(name = "ix_schedule_date", columnList = "start_date")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LessonScheduleEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;    // 레슨

    private LocalDate date;    // 레슨 실제 날짜

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;    // 레슨 요일

    private LocalTime startTime;    // 레슨 시작 시간

    private LocalTime endTime;      // 레슨 종료 시간

    private Integer capacityOverride;   // 레슨 정원(override)

    @Builder.Default
    private Integer reservedCount = 0;  // 레슨 예약 인원

    private String locationOverride;    // 레슨 장소(override)
}
