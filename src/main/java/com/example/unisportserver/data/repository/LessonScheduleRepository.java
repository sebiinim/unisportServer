package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.LessonScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LessonScheduleRepository extends JpaRepository<LessonScheduleEntity, Long> {
    boolean existsByLessonAndDateAndStartTime(LessonEntity lessonEntity, LocalDate date, LocalTime startTime);

    List<LessonScheduleEntity> findAllByLessonIdOrderByDateAscStartTimeAsc(Long lessonId);
}
