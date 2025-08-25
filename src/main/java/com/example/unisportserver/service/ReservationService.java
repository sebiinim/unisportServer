package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.ReservationRequestDto;
import com.example.unisportserver.data.dto.ReservationResponseDto;
import com.example.unisportserver.data.entity.ReservationEntity;
import com.example.unisportserver.data.mapper.ReservationMapper;
import com.example.unisportserver.data.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    // 예약 생성
    @Transactional
    public ReservationResponseDto saveReservation(ReservationRequestDto reservationRequestDto) {

        ReservationEntity reservationEntity = reservationMapper.toEntity(reservationRequestDto);

        reservationEntity.setCreatedAt(LocalDateTime.now());    // 현재 시간으로 설정

        reservationRepository.save(reservationEntity);

        return reservationMapper.toDto(reservationEntity);
    }

    // 예약 취소
    @Transactional
    public ReservationResponseDto deleteReservation(Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Reservation with id %s not found", reservationId))
        );

        ReservationResponseDto reservationResponseDto = reservationMapper.toDto(reservationEntity);

        reservationRepository.delete(reservationEntity);

        return reservationResponseDto;
    }

    // userId로 예약 조회
    @Transactional
    public Page<ReservationResponseDto> getReservationsByUserId(Long userId, Pageable pageable) {

        return reservationRepository.findAllByUserId(userId, pageable).map(reservationMapper::toDto);
    }
}
