package com.example.unisportserver.data.dto;

public class AttendanceResponseDto {
    Long attendanceId;

    Long lessonId;

    Long instructorUserId;      // 강사 id

    Long userId;        // 수강자 id

    Boolean isAttended;
}
