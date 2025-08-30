package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllById(Long lessonId);

    List<ReviewEntity> findAllByLessonId(Long lessonId);

    List<ReviewEntity> findAllByUserId(Long userId);

    List<ReviewEntity> findAllByLessonIdOrderByCreatedAtDesc(Long lessonId);

    List<ReviewEntity> findAllByLessonIdOrderByRatingDesc(Long lessonId);
}
