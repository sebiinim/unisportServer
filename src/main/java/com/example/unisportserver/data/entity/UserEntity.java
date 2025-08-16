package com.example.unisportserver.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
