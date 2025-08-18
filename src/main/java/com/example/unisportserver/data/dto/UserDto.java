package com.example.unisportserver.data.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDto {

    private String loginId;
    private String password;

    private String name;
    private String email;

    private String university;
    private String major;
    private int grade;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

// TODO: id 없애기 위해 Dto 분리 필요