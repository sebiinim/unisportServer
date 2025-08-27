package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.ReservationRequestDto;
import com.example.unisportserver.data.dto.ReservationResponseDto;
import com.example.unisportserver.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "reservation-controller", description = "예약 API")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @Operation(summary = "예약 생성", description = "Dto에 유의하세요")
    public ReservationResponseDto createReservation(@RequestBody ReservationRequestDto dto) {
        return reservationService.saveReservation(dto);
    }

    @DeleteMapping
    @Operation(summary = "예약 취소(lessonId, userId)", description = "lessonId와 userId 사용")
    public ReservationResponseDto deleteReservationByLessonIdAndUserId(@RequestParam Long lessonId, Long userId) {
        return reservationService.deleteReservationByLessonIdAndUserId(lessonId, userId);
    }

    @DeleteMapping(value = "/{reservationId}")
    @Operation(summary = "예약 취소(reservationId)", description = "reservationId 사용")
    public ReservationResponseDto deleteReservationByLessonIdAndUserId(@PathVariable Long reservationId) {
        return reservationService.deleteReservationByReservationId(reservationId);
    }

    @GetMapping(value = "/all")
    @Operation(summary = "전체 예약 조회", description = "서버의 모든 예약을 조회")
    public Page<ReservationResponseDto> getAllReservations(@Parameter(hidden = true) Pageable pageable) {
        return reservationService.getAllReservations(pageable);
    }


    @GetMapping(value = "/{userId}")
    @Operation(summary = "내 예약 조회", description = "유저의 전체 예약을 조회")
    public Page<ReservationResponseDto> getReservationsByUserId(@PathVariable Long userId, @Parameter(hidden = true) Pageable pageable) {
        return reservationService.getReservationsByUserId(userId, pageable);
    }

    @GetMapping(value = "/{userId}/{date}")
    @Operation(summary = "특정 날짜 예약 조회", description = "유저의 특정 날짜 예약을 조회")
    public Page<ReservationResponseDto> getReservationsByUserIdAndDate(
            @PathVariable Long userId, @PathVariable LocalDate date, @Parameter(hidden = true) Pageable pageable
    ) {
        return reservationService.getReservationsByUserIdAndDate(
                userId, date, pageable);
    }


}
