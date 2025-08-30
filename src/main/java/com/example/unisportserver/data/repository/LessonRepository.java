package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    boolean existsByInstructorUserId(Long InstructorUserId);

    List<LessonEntity> findAllBySport(String sport);


    @Query("""
                SELECT l FROM LessonEntity l
                WHERE LOWER(l.title)       LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(l.description) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(l.sport)       LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(l.location)    LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    List<LessonEntity> searchAnyField(String query);

    List<LessonEntity> findAllByLevel(Integer level);

    LessonEntity findByInstructorUserId(Long userId);
}
