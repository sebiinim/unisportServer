package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.ReservationRequestDto;
import com.example.unisportserver.data.dto.ReservationResponseDto;
import com.example.unisportserver.data.entity.AttendanceEntity;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReservationEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.enums.ReservationStatus;
import com.example.unisportserver.data.mapper.ReservationMapper;
import com.example.unisportserver.data.repository.AttendanceRepository;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.ReservationRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import jnr.constants.platform.Local;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceRepository attendanceRepository;

    // 예약 생성(출석도 생성)
    @Transactional
    public ReservationResponseDto saveReservation(ReservationRequestDto reservationRequestDto) {

        ReservationEntity reservationEntity = new ReservationEntity();

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

        boolean exists = attendanceRepository.existsByLessonAndUser(lessonEntity, userEntity);
        if (exists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "이미 이 수업에 대한 출석 기록이 있습니다. " + "id: " + lessonEntity.getId() + ", user: " + userEntity.getId()
            );
        }

        // FULL 인지 체크, 예약 인원&상태 변경
        if (lessonEntity.getReservation_status() == ReservationStatus.AVAILABLE) {
            lessonEntity.setReserved_count(lessonEntity.getReserved_count() + 1);

            if(lessonEntity.getReserved_count().equals(lessonEntity.getCapacity()) ) {
                lessonEntity.setReservation_status(ReservationStatus.FULL);
            }
        } else {    // FULL 인 경우
            throw new RuntimeException("수업의 정원이 가득 찼습니다.");
        }

        reservationEntity.setLesson(lessonEntity);

        reservationEntity.setCreatedAt(LocalDateTime.now());
        reservationEntity.setUpdatedAt(LocalDateTime.now());


        // 출석 table에 추가
        AttendanceEntity attendanceEntity = AttendanceEntity.builder()
                        .lesson(lessonEntity)
                        .user(userEntity)
                        .isAttended(null)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        attendanceRepository.save(attendanceEntity);

        reservationEntity.setAttenanceId(attendanceEntity.getId());

        reservationRepository.save(reservationEntity);

        return reservationMapper.toDto(reservationEntity);
    }

    // 예약 취소(lessonId, userId)
    @Transactional
    public ReservationResponseDto deleteReservationByLessonIdAndUserId(Long lessonId, Long userId) {

        // 예약 검색
        ReservationEntity reservationEntity = reservationRepository.findByLessonIdAndUserId(lessonId, userId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Reservation with lessonId %s and userId %s not found", lessonId, userId))
        );

        // 레슨의 예약 인원 낮추기, 상태 변경하기
        LessonEntity lessonEntity = reservationEntity.getLesson();

        lessonEntity.setReserved_count(lessonEntity.getReserved_count() - 1);
        lessonEntity.setReservation_status(ReservationStatus.AVAILABLE);

        lessonRepository.save(lessonEntity);

        // 예약 삭제
        ReservationResponseDto reservationResponseDto = reservationMapper.toDto(reservationEntity);

        reservationRepository.delete(reservationEntity);

        return reservationResponseDto;
    }

    // 예약 취소(reservationId)
    @Transactional
    public ReservationResponseDto deleteReservationByReservationId(Long reservationId) {

        // 예약 검색
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Reservation with reservationId %s not found", reservationId))
                );

        // 레슨의 예약 인원 낮추기, 상태 변경하기
        LessonEntity lessonEntity = reservationEntity.getLesson();

        lessonEntity.setReserved_count(lessonEntity.getReserved_count() - 1);
        lessonEntity.setReservation_status(ReservationStatus.AVAILABLE);

        lessonRepository.save(lessonEntity);

        // 예약 삭제
        ReservationResponseDto reservationResponseDto = reservationMapper.toDto(reservationEntity);

        reservationRepository.delete(reservationEntity);

        return reservationResponseDto;
    }

    
    // 서버의 모든 예약 조회
    @Transactional
    public Page<ReservationResponseDto> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(reservationMapper::toDto);
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
