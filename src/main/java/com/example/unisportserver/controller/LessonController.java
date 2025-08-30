package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LessonRequestDto;
import com.example.unisportserver.data.dto.LessonResponseDto;
import com.example.unisportserver.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
@Tag(name = "lesson-controller", description = "레슨 API")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    @Operation(summary = "레슨 생성", description = "DTO를 잘 보고 사용해주세요")
    public LessonResponseDto createLesson(@RequestBody LessonRequestDto lessonRequestDto) {
        return lessonService.saveLesson(lessonRequestDto);
    }

    @GetMapping(value = "/all")
    @Operation(summary = "모든 레슨 검색")
    public List<LessonResponseDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping(value = "/by-userId/{userId}")
    @Operation(summary = "내가 개설한 레슨 검색")
    public LessonResponseDto getLessonsByUserId(@PathVariable Long userId) {
        return lessonService.getLessonByUserId(userId);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "레슨 하나 검색")
    public LessonResponseDto getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping(value = "/by-sport/{sport}")
    @Operation(summary = "스포츠명으로 레슨 검색", description = "프론트에서 스포츠명을 지정해서 검색하게 해야할듯")
    public List<LessonResponseDto> getLessonsBySport(@PathVariable String sport) {
        return lessonService.getLessonsBySport(sport);
    }

    @GetMapping(value = "/by-keyword")
    @Operation(summary = "임의 키워드로 검색", description = "레슨 통합 검색 기능, 페이지로 제공함에 유의")
    public List<LessonResponseDto> searchAnyField(String query) {
        return lessonService.searchSimple(query);
    }

    @GetMapping(value = "/by-level/{level}")
    @Operation(summary = "레벨로 검색")
    public List<LessonResponseDto> getLessonsByLevel(@PathVariable Integer level) {
        return lessonService.getLessonsByLevel(level);
    }

    @GetMapping(value = "/by-date")
    @Operation(summary = "날짜로 레슨 검색", description = "날짜 형식은 2004-12-19")
    public List<LessonResponseDto> getLessonsByDate(@RequestParam LocalDate date) {
        return lessonService.getLessonsByDate(date);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "레슨 삭제하기")
    public LessonResponseDto deleteLessonById(@PathVariable Long id) {
        return lessonService.deleteLessonById(id);
    }

}
