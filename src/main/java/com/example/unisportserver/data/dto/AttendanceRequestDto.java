package com.example.unisportserver.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttendanceRequestDto {
    Long lessonId;

    Long instructorUserId;  // 강사 id

    Long userId;        // 수강자 id

    Boolean isAttended;
}
