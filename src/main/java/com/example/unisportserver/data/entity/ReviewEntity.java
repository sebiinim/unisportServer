package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "review", uniqueConstraints = @UniqueConstraint(
        name = "uk_review_lesson_student", columnNames = {"lesson_id", "student_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Integer rating;

    private String reviewContent;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
