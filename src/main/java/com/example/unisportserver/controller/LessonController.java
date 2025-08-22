package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
@Tag(name = "lesson-controller", description = "레슨 API")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    @Operation(summary = "레슨 등록", description = "DTO를 잘 보고 사용해주세요")
    public LessonDto createLesson(@RequestBody LessonDto lessonDto) {
        return lessonService.saveLesson(lessonDto);
    }

    @GetMapping(value = "/all")
    @Operation(summary = "모든 레슨 검색")
    public List<LessonDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "레슨 하나 검색")
    public LessonDto getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping(value = "by-date")
    @Operation(summary = "특정 날짜의 레슨 검색", description = "날짜 형식은 2004-12-19")
    public List<LessonDto> getLessonsByDate(@RequestParam LocalDate date) {
        return lessonService.getLessonsByDate(date);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "레슨 삭제하기")
    public LessonDto deleteLessonById(@PathVariable Long id) {
        return lessonService.deleteLessonById(id);
    }

}
