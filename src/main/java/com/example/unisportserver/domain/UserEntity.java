package com.example.unisportserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity {

    @Id
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
