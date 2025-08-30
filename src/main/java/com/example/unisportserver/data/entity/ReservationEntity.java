package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation",
        // 수업 중복 예약 방지
        uniqueConstraints = @UniqueConstraint(name = "uk_reservation_user_lesson", columnNames = {"user_id", "lesson_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_schedule_id")
    private LessonScheduleEntity lessonSchedule;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long attenanceId;   // 이 예약에 연결된 출석체크 엔티티의 id
}
