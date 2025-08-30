package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.LessonLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonLikeRepository extends JpaRepository<LessonLikeEntity, Long> {

    boolean existsByUserIdAndLessonId(long userId, long lessonId);

    long countByLessonId(long lessonId);

    Optional<LessonLikeEntity> findByUserIdAndLessonId(long userId, long lessonId);

    void deleteByUserIdAndLessonId(long userId, long lessonId);

    Page<LessonEntity> findAllByUserId(Long userId, Pageable pageable);

    @Query(
            value = """
            select distinct le
            from LessonLikeEntity ll
            join ll.lesson le
            where ll.user.id = :userId
        """,
            countQuery = """
            select count(distinct le.id)
            from LessonLikeEntity ll
            join ll.lesson le
            where ll.user.id = :userId
        """
    )
    Page<LessonEntity> findLikedLessonsByUserId(@Param("userId") Long userId, Pageable pageable);
}
