package com.example.unisportserver.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private Long lessonId;    // 리뷰 대상 수업
    private Long UserId;     // 리뷰 작성자
    private Integer rating;
    private String reviewContent;
    private LocalDateTime createdAt;
}
