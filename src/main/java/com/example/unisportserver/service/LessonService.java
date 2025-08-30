package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LessonRequestDto;
import com.example.unisportserver.data.dto.LessonResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.LessonMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final UserRepository userRepository;

    // 레슨 생성
    @Transactional
    public LessonResponseDto saveLesson(LessonRequestDto lessonRequestDto) {

        // 강사 존재 확인
        UserEntity instructorUser = userRepository.findById(lessonRequestDto.getInstructorUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("User with id %s not found", lessonRequestDto.getInstructorUserId())
                ));

        // 강사가 다른 수업을 하고 있는지 확인
        if (lessonRepository.existsByInstructorUserId(instructorUser.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, String.format("User with id %s already has a lesson", instructorUser.getId())
            );
        }

        LessonEntity lessonEntity = lessonMapper.toEntity(lessonRequestDto);

        lessonRepository.save(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }

    // 모든 레슨 검색
    public List<LessonResponseDto> getAllLessons() {
        List<LessonEntity> lessonEntities = lessonRepository.findAll();

        return lessonMapper.toDtoList(lessonEntities);
    }

    // 내 레슨 조회
    public LessonResponseDto getLessonByUserId(Long userId) {
        LessonEntity lessonEntity = lessonRepository.findByInstructorUserId(userId);

        return lessonMapper.toDto(lessonEntity);
    }

    // lessonId로 레슨 검색
    public LessonResponseDto getLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("lesson with id %d not found", id)));
        return lessonMapper.toDto(lessonEntity);
    }

    // sport로 레슨 검색
    public List<LessonResponseDto> getLessonsBySport(String sport) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllBySport(sport);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // date로 레슨 검색
    public List<LessonResponseDto> getLessonsByDate(LocalDate date) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllByLessonDate(date);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // 키워드로 레슨 검색
    public List<LessonResponseDto> searchSimple(String query) {
        List<LessonEntity> lessonEntities = lessonRepository.searchAnyField(query);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // 레벨로 레슨 검색
    public List<LessonResponseDto> getLessonsByLevel(Integer level) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllByLevel(level);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // id로 레슨 삭제
    public LessonResponseDto deleteLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Lesson with id %s not found", id)
        ));

        lessonRepository.delete(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }
}
