package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping(value = "/all")
    public List<LessonDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping(value = "/{id}")
    public LessonDto getLessonById(@PathVariable String id) {
        return lessonService.getLessonById(id);
    }

    @PostMapping
    public LessonDto createLesson(@RequestBody LessonDto lessonDto) {
        return lessonService.saveLesson(lessonDto);
    }

    @DeleteMapping(value = "/{id}")
    public LessonDto deleteLessonById(@PathVariable String id) {
        return lessonService.deleteLessonById(id);
    }

}
