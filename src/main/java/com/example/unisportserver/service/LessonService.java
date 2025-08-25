package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.LessonMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    // 레슨 생성
    @Transactional
    public LessonDto saveLesson(LessonDto lessonDto) {
        UserEntity instructorUser = userRepository.findById(lessonDto.getInstructorUserId())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("User with id %s not found", lessonDto.getInstructorUserId())
                ));

        if(lessonRepository.existsByInstructorUserId(instructorUser.getId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, String.format("User with id %s already has a lesson", instructorUser.getId())
            );
        }

        LessonEntity lessonEntity = lessonMapper.toEntity(lessonDto);

        lessonRepository.save(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }

    // 모든 레슨 검색
    public List<LessonDto> getAllLessons() {
        List<LessonEntity> lessonEntities = lessonRepository.findAll();

        return lessonMapper.toDtoList(lessonEntities);
    }

    // lessonId로 레슨 검색
    public LessonDto getLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("lesson with id %d not found", id) ));
        return lessonMapper.toDto(lessonEntity);
    }

    // sport로 레슨 검색
    public List<LessonDto> getLessonsBySport(String sport) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllBySport(sport);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // date로 레슨 검색
    public List<LessonDto> getLessonsByDate(LocalDate date) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllByLessonDate(date);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // 키워드로 레슨 검색
    public Page<LessonDto> searchSimple(String q, Pageable pageable) {
        return lessonRepository.searchAnyField(q, pageable).map(lessonMapper::toDto);
    }

    // 레벨로 레슨 검색
    public List<LessonDto> getLessonsByLevel(Integer level) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllByLevel(level);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // id로 레슨 삭제
    public LessonDto deleteLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Lesson with id %s not found", id)
        ));

        lessonRepository.delete(lessonEntity);

        return lessonMapper.toDto(lessonEntity);
    }
}
