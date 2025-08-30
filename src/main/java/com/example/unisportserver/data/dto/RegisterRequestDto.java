package com.example.unisportserver.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    private String loginId;
    private String password;

    private String name;
    private String email;

    private String university;
    private String studentNumber;     // 학번
}