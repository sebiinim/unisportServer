package com.example.unisportserver.data.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String loginId;
    private String password;

    private String name;
    private String email;

    private String university;
    private String studentNumber;

    private Boolean isInstructor;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
