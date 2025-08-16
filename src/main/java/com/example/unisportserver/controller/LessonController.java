package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService = lessonService;
    }

    @GetMapping(value = "/all")
    public String getAllLessons() {
        return "";
    }

    @GetMapping(value = "/{id}")
    public LessonDto getLessonById(@PathVariable String id) {
        return lessonService.getLessonById(id);
    }

    @PostMapping
    public LessonDto createLesson(@RequestBody LessonDto lessonDto) {

        String ID = lessonDto.getId();
        String title =  lessonDto.getTitle();
        String description = lessonDto.getDescription();
        String sport = lessonDto.getSport();

        return lessonService.saveLesson(ID, title, description, sport);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteLessonById(@PathVariable String id) {
        return null;
    }

}
