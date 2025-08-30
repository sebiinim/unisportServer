package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance", uniqueConstraints = @UniqueConstraint(
        name = "uk_attendance_lesson_user", columnNames = {"lesson_id", "user_id"}
))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private LessonEntity lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    private Boolean isAttended;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
