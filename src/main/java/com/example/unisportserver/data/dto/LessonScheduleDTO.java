package com.example.unisportserver.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonScheduleDTO {
    private String id;
    private String date;         // yyyy-MM-dd 형식
    private String startTime;    // HH:mm 형식
    private String endTime;      // HH:mm 형식
    private int availableSpots;
}
