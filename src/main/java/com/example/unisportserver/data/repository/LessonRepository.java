package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.entity.LessonEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    Page<LessonEntity> searchAnyField(@Param("q") String q, Pageable pageable);

    List<LessonEntity> findAllByLevel(Integer level);

    LessonEntity findByInstructorUserId(Long userId);
}
