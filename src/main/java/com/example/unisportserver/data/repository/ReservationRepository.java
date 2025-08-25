package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    Page<ReservationEntity> findAllByUserId(Long userId, Pageable pageable);

    Page<ReservationEntity> findAllByUser_IdAndLesson_LessonDate(Long userId, LocalDate date, Pageable pageable);
}
