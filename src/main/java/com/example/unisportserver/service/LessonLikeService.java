package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LessonResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.LessonLikeEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.LessonMapper;
import com.example.unisportserver.data.repository.LessonLikeRepository;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonLikeService {
    private final LessonLikeRepository lessonLikeRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final LessonMapper lessonMapper;

    // 관심 레슨 등록
    @Transactional
    public LikeResult like(Long lessonId, Long userId) {
        if (lessonLikeRepository.existsByUserIdAndLessonId(userId, lessonId)) {
            return LikeResult.ALREADY_EXISTS;   // 이미 관심 상태면 무시
        }
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("userid : " + userId + " not found"));
        LessonEntity lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new RuntimeException("lessonid : " + lessonId + " not found"));

        LessonLikeEntity lessonLikeEntity = LessonLikeEntity.builder()
                .user(user)
                .lesson(lesson)
                .createdAt(LocalDateTime.now())
                .build();
        lessonLikeRepository.save(lessonLikeEntity);

        return LikeResult.CREATED;
    }

    // 관심 레슨 등록 취소
    @Transactional
    public LikeResult unlike(Long userId, Long lessonId) {
        if (!lessonLikeRepository.existsByUserIdAndLessonId(userId, lessonId)) {
            return LikeResult.NOT_FOUND;     // 좋아요가 없으면 무시
        }
        lessonLikeRepository.deleteByUserIdAndLessonId(userId, lessonId);
        return LikeResult.DELETED;
    }

    // 관심 레슨인지 확인
    @Transactional
    public boolean isliked(Long userId, Long lessonId) {
        return lessonLikeRepository.existsByUserIdAndLessonId(userId, lessonId);
    }

    // 유저의 관심 레슨 모두 조회
    @Transactional
    public List<LessonResponseDto> getUserLikeLessons(Long userId) {
        List<LessonEntity> lessonEntities = lessonLikeRepository.findLikedLessonsByUserId(userId);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // 레슨에 관심있는 사람 수
    @Transactional
    public long countLikes(Long lessonId) {
        return lessonLikeRepository.countByLessonId(lessonId);
    }

    @Transactional
    public LikeResult toggle(Long userId, Long lessonId) {

        return lessonLikeRepository.findByUserIdAndLessonId(userId, lessonId)
                .map(existing -> {
                    lessonLikeRepository.delete(existing);
                    return LikeResult.DELETED;   // 좋아요 취소
                })
                .orElseGet(() -> {
                    like(userId, lessonId);
                    return LikeResult.CREATED;    // 좋아요
                });
    }

    public enum LikeResult {CREATED, ALREADY_EXISTS, DELETED, NOT_FOUND}
}
