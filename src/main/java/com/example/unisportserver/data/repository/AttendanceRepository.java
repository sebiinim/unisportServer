package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.dto.AttendanceResponseDto;
import com.example.unisportserver.data.entity.AttendanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    AttendanceEntity findByLessonIdAndUserId(Long lessonId, Long userId);

    Page<AttendanceResponseDto> findAllByLessonId(Long lessonId, Pageable pageable);
}
