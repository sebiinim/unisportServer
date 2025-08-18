package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReviewEntity;
import com.example.unisportserver.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllById(Long lessonId);

    List<ReviewEntity> findAllByLesson_Id(Long lessonId);

    List<ReviewEntity> findAllByUser_Id(Long userId);
}
