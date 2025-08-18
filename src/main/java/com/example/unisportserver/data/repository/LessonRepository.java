package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    boolean existsByInstructorUserId(Long InstructorUserId);

    List<LessonEntity> findAllByLessonDate(LocalDate lessonDate);
}
