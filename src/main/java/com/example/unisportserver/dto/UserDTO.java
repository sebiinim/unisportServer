package com.example.unisportserver.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String university;
    private String major;
    private int grade;
    private String bio;
    private double rating;
    private int reviewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
