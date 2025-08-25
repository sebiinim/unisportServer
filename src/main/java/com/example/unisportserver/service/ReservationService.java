package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.ReservationRequestDto;
import com.example.unisportserver.data.dto.ReservationResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReservationEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.ReservationMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.ReservationRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, UserRepository userRepository, LessonRepository lessonRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }

    // 예약 생성
    @Transactional
    public ReservationResponseDto saveReservation(ReservationRequestDto reservationRequestDto) {

        ReservationEntity reservationEntity = new  ReservationEntity();

        // 유저 찾아서 예약 엔티티에 추가
        UserEntity userEntity = userRepository.findById(reservationRequestDto.getUserId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("User with id %s not found", reservationRequestDto.getUserId())
                )
        );
        reservationEntity.setUser(userEntity);


        // 레슨 찾아서 예약 엔티티에 추가
        LessonEntity lessonEntity = lessonRepository.findById(reservationRequestDto.getLessonId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Lesson id %s not found", reservationRequestDto.getLessonId())
                )
        );
        reservationEntity.setLesson(lessonEntity);

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

    // userId로 전체 예약 조회
    @Transactional
    public Page<ReservationResponseDto> getReservationsByUserId(Long userId, Pageable pageable) {

        return reservationRepository.findAllByUserId(userId, pageable).map(reservationMapper::toDto);
    }

    // userId로 예약 조회
    @Transactional
    public Page<ReservationResponseDto> getReservationsByUserIdAndDate(Long userId, LocalDate date, Pageable pageable) {

        return reservationRepository.findAllByUser_IdAndLesson_LessonDate(userId, date, pageable).map(reservationMapper::toDto);
    }
}
