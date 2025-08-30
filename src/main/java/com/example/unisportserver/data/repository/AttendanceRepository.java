package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.AttendanceEntity;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    AttendanceEntity findByLessonIdAndUserId(Long lessonId, Long userId);

    List<AttendanceEntity> findAllByLessonId(Long lessonId);

    boolean existsByLessonAndUser(LessonEntity lessonEntity, UserEntity userEntity);
}
