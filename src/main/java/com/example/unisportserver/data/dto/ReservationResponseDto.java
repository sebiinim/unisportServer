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
public class ReservationResponseDto {

    private Long id;

    private Long userId;

    private Long lessonScheduleId;

    private LocalDateTime createdAt;

    private Long attenanceId;
}
