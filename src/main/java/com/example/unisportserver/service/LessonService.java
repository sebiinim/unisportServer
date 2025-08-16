package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.mapper.LessonMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    // 모든 레슨 검색
    public List<LessonDto> getAllLessons() {
        List<LessonEntity> lessonEntities = lessonRepository.findAll();

        return lessonMapper.toDtoList(lessonEntities);
    }

    // id로 레슨 검색
    public LessonDto getLessonById(String id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id : " + id + " lesson not found"));

        return lessonMapper.toDto(lessonEntity);
    }

    // 레슨 생성
    public LessonDto saveLesson(LessonDto lessonDto) {
        LessonEntity lessonEntity = lessonMapper.toEntity(lessonDto);

        lessonRepository.save(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }

    // id로 레슨 삭제
    public LessonDto deleteLessonById(String id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new  IllegalArgumentException("id : " + id + " lesson not found"));

        lessonRepository.delete(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }
}
