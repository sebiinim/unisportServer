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

    private String level;

    private String location;

    private Long instructorUserId;

    private LocalDate lessonDate;

    private LocalTime lessonTime;

}
