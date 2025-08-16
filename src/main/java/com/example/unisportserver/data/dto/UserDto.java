package com.example.unisportserver.data.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDto {
    private String id;
    private String name;
    private String email;

    private String university;
    private String major;
    
    private int grade;      // 학년
    private double rating;  // 평점
    private int reviewCount;    // 리뷰 작성 횟수

    private LocalDateTime createdAt;    // 가입 시각
    private LocalDateTime updatedAt;    // 업데이트 시각
}

// TODO: id 없애기 위해 Dto 분리 필요