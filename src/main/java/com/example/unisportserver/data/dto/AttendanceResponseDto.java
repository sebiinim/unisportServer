package com.example.unisportserver.data.dto;

import java.time.LocalDateTime;

public class AttendanceResponseDto {
    Long attendanceId;

    Long lessonId;

    Long instructorUserId;      // 강사 id

    Long userId;        // 수강자 id

    Boolean isAttended;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
