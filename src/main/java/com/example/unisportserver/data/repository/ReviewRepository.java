package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllById(Long lessonId);
}
