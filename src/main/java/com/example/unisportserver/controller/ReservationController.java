package com.example.unisportserver.controller;

import com.example.unisportserver.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "reservation-controller", description = "예약 API")
public class ReservationController {
    private final ReservationService reservationService;

//    @PostMapping
//    @Operation(summary = "예약 생성", description = "Dto에 유의하세요")
//    public

}
