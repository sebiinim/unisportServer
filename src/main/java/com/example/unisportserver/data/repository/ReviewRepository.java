package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllById(Long lessonId);

    Page<ReviewEntity> findAllByLessonId(Long lessonId, Pageable pageable);

    List<ReviewEntity> findAllByUserId(Long userId);
}
