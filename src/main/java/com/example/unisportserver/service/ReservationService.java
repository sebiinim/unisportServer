package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.ReservationRequestDto;
import com.example.unisportserver.data.dto.ReservationResponseDto;
import com.example.unisportserver.data.entity.*;
import com.example.unisportserver.data.mapper.ReservationMapper;
import com.example.unisportserver.data.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceRepository attendanceRepository;
    private final LessonScheduleRepository lessonScheduleRepository;

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


        // 레슨 일정 찾아서 예약 엔티티에 추가
        LessonScheduleEntity lessonScheduleEntity = lessonScheduleRepository.findById(reservationRequestDto.getLessonScheduleId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Lesson id %s not found", reservationRequestDto.getLessonScheduleId())
                )
        );

        // 자신의 레슨인지 확인
        if (userEntity.getId().equals(lessonScheduleEntity.getLesson().getInstructorUserId()) ) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "자신이 강사인 수업은 예약할 수 없습니다."
            );
        }

        // 예약 중복 확인
        boolean exists = reservationRepository.existsByLessonScheduleAndUser(lessonScheduleEntity, userEntity);
        if (exists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "이미 이 수업을 예약했습니다. " + "lessonScheduleId: " + lessonScheduleEntity.getId() + ", userId: " + userEntity.getId()
            );
        }

        // FULL 인지 체크, 예약 인원&상태 변경
        if (lessonScheduleEntity.getReservedCount() >= lessonScheduleEntity.getCapacityOverride()) {
            throw new RuntimeException("수업의 정원이 가득 찼습니다.");
        }

        reservationEntity.setLessonSchedule(lessonScheduleEntity);

        reservationEntity.setCreatedAt(LocalDateTime.now());
        reservationEntity.setUpdatedAt(LocalDateTime.now());


        // 출석 table에 추가
        AttendanceEntity attendanceEntity = AttendanceEntity.builder()
                .lessonSchedule(lessonScheduleEntity)
                .user(userEntity)
                .isAttended(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        attendanceRepository.save(attendanceEntity);


        // 예약 엔티티에 방금 만든 출석 엔티티의 id를 추가
        reservationEntity.setAttendanceId(attendanceEntity.getId());

        reservationRepository.save(reservationEntity);

        return reservationMapper.toDto(reservationEntity);
    }

    // 예약 취소(lessonScheduleId, userId)
    @Transactional
    public ReservationResponseDto deleteReservationByLessonIdAndUserId(ReservationRequestDto reservationRequestDto) {

        Long lessonScheduleId = reservationRequestDto.getLessonScheduleId();
        Long userId = reservationRequestDto.getUserId();

        // 예약 검색
        ReservationEntity reservationEntity = reservationRepository.findByLessonScheduleIdAndUserId(lessonScheduleId, userId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Reservation with lessonScheduleId %s and userId %s not found", lessonScheduleId, userId))
                );

        // 레슨의 예약 인원 낮추기
        LessonScheduleEntity lessonScheduleEntity = reservationEntity.getLessonSchedule();

        lessonScheduleEntity.setReservedCount(lessonScheduleEntity.getReservedCount() - 1);

        lessonScheduleRepository.save(lessonScheduleEntity);


        // 출석 삭제
        AttendanceEntity attendanceEntity = attendanceRepository.findByLessonScheduleIdAndUserId(lessonScheduleId, userId);

        attendanceRepository.delete(attendanceEntity);

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
        LessonScheduleEntity lessonScheduleEntity = reservationEntity.getLessonSchedule();

        lessonScheduleEntity.setReservedCount(lessonScheduleEntity.getReservedCount() - 1);

        lessonScheduleRepository.save(lessonScheduleEntity);

        // 예약 삭제
        ReservationResponseDto reservationResponseDto = reservationMapper.toDto(reservationEntity);

        reservationRepository.delete(reservationEntity);

        return reservationResponseDto;

        // TODO: attendance 삭제
    }


    // 서버의 모든 예약 조회
    @Transactional
    public List<ReservationResponseDto> getAllReservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();

        return reservationMapper.toDtoList(reservationEntities);
    }

    // userId로 전체 예약 조회
    @Transactional
    public List<ReservationResponseDto> getReservationsByUserId(Long userId) {

        List<ReservationEntity> reservationEntities = reservationRepository.findAllByUserId(userId);

        return reservationMapper.toDtoList(reservationEntities);
    }

    // userId로 특정 날짜 예약 조회
    @Transactional
    public List<ReservationResponseDto> getReservationsByUserIdAndDate(Long userId, LocalDate date) {

        List<ReservationEntity> reservationEntities = reservationRepository.findAllByUser_IdAndLessonSchedule_Date(userId, date);

        return reservationMapper.toDtoList(reservationEntities);
    }
}
