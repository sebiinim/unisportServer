package com.example.unisportserver.data.entity;

import com.example.unisportserver.data.enums.DayOfTheWeek;
import com.example.unisportserver.data.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

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

    private String sport;       // 대분류 (축구)

    private String title;       // 중분류 (중급 축구)

    private String description;

    private Integer level;

    private String location;

    private Integer capacity;           // 수업 정원

    private Integer reservedCount;     // 예약 인원

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;     // 정원 상태

    private String imagePath;

    private Boolean isEveryWeek;

    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek;   // 요일

    private Long instructorUserId;

    private LocalDate lessonDate;

    private LocalTime lessonTime;
}


