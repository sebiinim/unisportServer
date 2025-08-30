package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "lesson_like",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_lesson_like_user_lesson", columnNames = {"user_id", "lesson_id"})
        },
        indexes = {
                @Index(name = "idx_lesson_like_leeon", columnList = "lesson_id"),
                @Index(name = "idx_lesson_like_user", columnList = "user_id")
        })
@Builder
public class LessonLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;

    private LocalDateTime createdAt;

}
