package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    boolean existsByInstructorUserId(Long InstructorUserId);

    List<LessonEntity> findAllByLessonDate(LocalDate lessonDate);

    List<LessonEntity> findAllBySport(String sport);


    @Query("""
                SELECT l FROM LessonEntity l
                WHERE LOWER(l.title)       LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(l.description) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(l.sport)       LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(l.location)    LIKE LOWER(CONCAT('%', :q, '%'))
            """)
    List<LessonEntity> searchAnyField(String query);

    List<LessonEntity> findAllByLevel(Integer level);

    LessonEntity findByInstructorUserId(Long userId);
}
