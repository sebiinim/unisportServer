package com.example.unisportserver.service;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.LessonLikeEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.repository.LessonLikeRepository;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LessonLikeService {
    private final LessonLikeRepository lessonLikeRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public enum LikeResult { CREATED, ALREADY_EXISTS, DELETED, NOT_FOUND }

    @Transactional
    public LikeResult like(Long lessonId, Long userId) {
        if (lessonLikeRepository.existsByUserIdAndLessonId(userId, lessonId)) {
            return LikeResult.ALREADY_EXISTS;   // 이미 좋아요 눌렀으면 무시
        }
        UserEntity user =  userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("userid : " + userId + " not found"));
        LessonEntity lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new IllegalArgumentException("lessonid : " + lessonId + " not found"));

        LessonLikeEntity lessonLikeEntity = LessonLikeEntity.builder()
                        .user(user)
                        .lesson(lesson)
                        .createdAt(LocalDateTime.now())
                        .build();
        lessonLikeRepository.save(lessonLikeEntity);

        return LikeResult.CREATED;
    }

    @Transactional
    public LikeResult unlike(Long userId, Long lessonId) {
        if (!lessonLikeRepository.existsByUserIdAndLessonId(userId, lessonId)) {
            return LikeResult.NOT_FOUND;     // 좋아요가 없으면 무시
        }
        lessonLikeRepository.deleteByUserIdAndLessonId(userId, lessonId);
        return LikeResult.DELETED;
    }

    @Transactional
    public boolean isliked(Long userId, Long lessonId) {
        return lessonLikeRepository.existsByUserIdAndLessonId(userId, lessonId);
    }

    @Transactional
    public long countLikes(Long lessonId) {
        return lessonLikeRepository.countByLessonId(lessonId);
    }

    @Transactional
    public boolean toggle(Long userId, Long lessonId) {

        return lessonLikeRepository.findByUserIdAndLessonId(userId, lessonId)
                .map(existing -> {
                    lessonLikeRepository.delete(existing);
                    return false;   // 좋아요 취소
                })
                .orElseGet(() -> {
                    like(userId, lessonId);
                    return true;    // 좋아요
                });
    }
}
