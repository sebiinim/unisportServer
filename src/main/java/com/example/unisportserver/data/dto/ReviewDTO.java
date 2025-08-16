package com.example.unisportserver.data.dto;

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
    private LessonDto lesson;    // 리뷰 대상 수업
    private UserDto student;     // 리뷰 작성자
    private double rating;
    private String comment;
    private LocalDateTime createdAt;
}
