package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findAllByUserId(Long userId);

    List<ReservationEntity> findAllByUser_IdAndLessonSchedule_Date(Long userId, LocalDate date);

    Optional<ReservationEntity> findByLessonScheduleIdAndUserId(Long lessonScheduleId, Long userId);
}
