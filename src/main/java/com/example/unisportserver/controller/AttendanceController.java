package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.AttendanceRequestDto;
import com.example.unisportserver.data.dto.AttendanceResponseDto;
import com.example.unisportserver.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "attendance-controller", description = "출석 API")
@RequestMapping(value = "/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping(value = "/attendance-true")
    @Operation(summary = "출석 체크", description = "강사가 자신의 레슨 출석을 체크")
    public AttendanceResponseDto markAttendanceTrue(@RequestBody AttendanceRequestDto attendanceRequestDto) {

        return attendanceService.markAttendanceTrue(attendanceRequestDto);
    }

    @PostMapping(value = "attendance-false")
    @Operation(summary = "결석 체크", description = "강사가 자신의 레슨 결석을 체크")
    public AttendanceResponseDto markAttendanceFalse(@RequestBody AttendanceRequestDto attendanceRequestDto) {

        return attendanceService.markAttendanceFalse(attendanceRequestDto);
    }

    @GetMapping(value = "/{lessonId}")
    @Operation(summary = "레슨의 출석 현황 확인", description = "레슨의 전체 출석자를 체크")
    public List<AttendanceResponseDto> getAttendance(@PathVariable Long lessonId) {

        return attendanceService.getAttendanceByLessonId(lessonId);
    }

}
