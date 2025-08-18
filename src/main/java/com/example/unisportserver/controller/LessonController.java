package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public LessonDto createLesson(@RequestBody LessonDto lessonDto) {
        return lessonService.saveLesson(lessonDto);
    }

    @GetMapping(value = "/all")
    public List<LessonDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping(value = "/{id}")
    public LessonDto getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping(value = "by-date")
    public List<LessonDto> getLessonsByDate(@RequestParam LocalDate date) {
        return lessonService.getLessonsByDate(date);
    }


    @DeleteMapping(value = "/{id}")
    public LessonDto deleteLessonById(@PathVariable Long id) {
        return lessonService.deleteLessonById(id);
    }

}
