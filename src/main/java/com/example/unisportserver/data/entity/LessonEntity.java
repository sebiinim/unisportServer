package com.example.unisportserver.data.entity;

import com.example.unisportserver.data.enums.DayOfTheWeek;
import com.example.unisportserver.data.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "lesson",
indexes = {
        @Index(name = "ix_lesson_instructor", columnList = "instructor_user_id"),
        @Index(name = "ix_lesson_sport_title", columnList = "sport, title")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sport;       // 대분류 (축구)
    private String title;       // 소분류 (중급 축구)
    private String description; // 수업 설명
    private Integer level;      // 수업 난이도

    @Column(name = "instructor_user_id")
    private Long instructorUserId;  // 강사의 유저 id

    private String location;    // 수업 장소
    private Integer capacity;   // 수업 정원
    private String imagePath;   // 이미지 경로
}


