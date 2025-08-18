package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.LessonMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final UserRepository userRepository;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.userRepository = userRepository;
    }

    // 모든 레슨 검색
    public List<LessonDto> getAllLessons() {
        List<LessonEntity> lessonEntities = lessonRepository.findAll();

        return lessonMapper.toDtoList(lessonEntities);
    }

    // id로 레슨 검색
    public LessonDto getLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id : " + id + " lesson not found"));

        return lessonMapper.toDto(lessonEntity);
    }

    // date로 레슨 검색
    public List<LessonDto> getLessonsByDate(LocalDate date) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllByLessonDate(date);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // 레슨 생성
    @Transactional
    public LessonDto saveLesson(LessonDto lessonDto) {
        UserEntity instructorUser = userRepository.findById(lessonDto.getInstructorUserId())
                .orElseThrow(()-> new IllegalArgumentException("userId : " + lessonDto.getInstructorUserId() + " instructor not found"));

        if(lessonRepository.existsByInstructorUserId(instructorUser.getId())){
            throw new IllegalStateException("userId : " + instructorUser.getId() + " instructor already has a lesson");
        }

        LessonEntity lessonEntity = lessonMapper.toEntity(lessonDto);

        lessonRepository.save(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }

    // id로 레슨 삭제
    public LessonDto deleteLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new  IllegalArgumentException("id : " + id + " lesson not found"));

        lessonRepository.delete(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }
}
