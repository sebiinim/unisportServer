package com.example.unisportserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private String id;
    private LessonDTO lesson;    // 리뷰 대상 수업
    private UserDTO student;     // 리뷰 작성자
    private double rating;
    private String comment;
    private LocalDateTime createdAt;
}
