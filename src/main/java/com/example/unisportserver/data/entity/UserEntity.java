package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String loginId;
    private String password;

    private String name;
    private String email;

    private String university;
    private String major;

    private int grade;
    private int rating;
    private int reviewCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
