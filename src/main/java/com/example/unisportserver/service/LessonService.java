package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LessonRequestDto;
import com.example.unisportserver.data.dto.LessonResponseDto;
import com.example.unisportserver.data.dto.LessonScheduleResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.LessonScheduleEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.LessonMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.LessonScheduleRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import jnr.constants.platform.Local;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final LessonScheduleRepository lessonScheduleRepository;
    private final UserRepository userRepository;

    // 레슨 개설 (스케줄 포함)
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

        // 1) 레슨 객체 생성
        LessonEntity lessonEntity = lessonMapper.toEntity(lessonRequestDto);

        lessonRepository.save(lessonEntity);

        // 2) 레슨 스케줄 등록
        int intervalWeeks = lessonRequestDto.getIntervalWeeks();

        LocalDate firstDate = lessonRequestDto.getStartDate();
        DayOfWeek dow = firstDate.getDayOfWeek();

        List<LessonScheduleEntity> created = new ArrayList<>(lessonRequestDto.getTotalCount());

        for (int i = 0; i < lessonRequestDto.getTotalCount(); i++) {
            LocalDate date = firstDate.plusWeeks((long) i * intervalWeeks);     // 레슨 실제 날짜

            // 유니크 제약 보호(중복 생성 방지)
            boolean exists = lessonScheduleRepository.existsByLessonAndDateAndStartTime(lessonEntity, date, lessonRequestDto.getStartTime());
            if (exists) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Schedule already exists for lessonId=%d at %s %s"
                                .formatted(lessonEntity.getId(), date, lessonRequestDto.getStartTime())
                );
            }

            LessonScheduleEntity lessonScheduleEntity = LessonScheduleEntity.builder()
                    .lesson(lessonEntity)
                    .date(date)
                    .dayOfWeek(dow)
                    .startTime(lessonRequestDto.getStartTime())
                    .endTime(lessonRequestDto.getEndTime())
                    .capacityOverride(lessonEntity.getCapacity())
                    .reservedCount(0)
                    .locationOverride(lessonEntity.getLocation())
                    .build();

            lessonScheduleRepository.save(lessonScheduleEntity);
            created.add(lessonScheduleEntity);



        }

        // 3) 응답 구성
        List<LessonScheduleResponseDto> lessonScheduleResponseDtos = created.stream()
                .sorted(Comparator.comparing(LessonScheduleEntity::getDate)
                        .thenComparing(LessonScheduleEntity::getStartTime))
                .map(s -> LessonScheduleResponseDto.builder()
                        .id(s.getId())
                        .lessonId(lessonEntity.getId())
                        .date(s.getDate())
                        .dayOfWeek(s.getDayOfWeek())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        .capacity(s.getCapacityOverride() != null ? s.getCapacityOverride() : lessonEntity.getCapacity())
                        .reservedCount(s.getReservedCount() == null ? 0 : s.getReservedCount())
                        .location(s.getLocationOverride() != null ? s.getLocationOverride() : lessonEntity.getLocation())
                        .build())
                .toList();


        return LessonResponseDto.builder()
                .id(lessonEntity.getId())
                .sport(lessonEntity.getSport())
                .title(lessonEntity.getTitle())
                .description(lessonEntity.getDescription())
                .level(lessonEntity.getLevel())
                .instructorUserId(lessonEntity.getInstructorUserId())
                .location(lessonEntity.getLocation())
                .capacity(lessonEntity.getCapacity())
                .imagePath(lessonEntity.getImagePath())
                .schedules(lessonScheduleResponseDtos)
                .build();
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

    // lessonId로 레슨 하나 검색 (스케줄 List 포함)
    public LessonResponseDto getLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("lesson with id %d not found", id)));
        LessonResponseDto lessonResponseDto = lessonMapper.toDto(lessonEntity);


        // 레슨 스케줄 Dto의 List 생성
        List<LessonScheduleEntity> schedules =
                lessonScheduleRepository.findAllByLessonIdOrderByDateAscStartTimeAsc(lessonEntity.getId());
        List<LessonScheduleResponseDto> scheduleDtos = schedules.stream()
                .map(s -> LessonScheduleResponseDto.builder()
                        .id(s.getId())
                        .lessonId(lessonEntity.getId())
                        .date(s.getDate())
                        .dayOfWeek(s.getDayOfWeek())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        // override가 없으면 lesson 기준값으로 대체
                        .capacity(s.getCapacityOverride() != null ? s.getCapacityOverride() : lessonEntity.getCapacity())
                        .reservedCount(s.getReservedCount() == null ? 0 : s.getReservedCount())
                        .location(s.getLocationOverride() != null ? s.getLocationOverride() : lessonEntity.getLocation())
                        .build()
                )
                .toList();

        lessonResponseDto.setSchedules(scheduleDtos);

        return lessonResponseDto;
    }

    // sport로 레슨 검색
    public List<LessonResponseDto> getLessonsBySport(String sport) {
        List<LessonEntity> lessonEntities = lessonRepository.findAllBySport(sport);

        return lessonMapper.toDtoList(lessonEntities);
    }

    // 임의 키워드로 레슨 검색
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
