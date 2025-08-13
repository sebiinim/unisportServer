package com.example.unisportserver.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @GetMapping(value = "/all")
    public String getAllLessons() {
        return "전체 레슨 정보를 줄 예정";
    }

    @GetMapping(value = "/search/{lessonID}")
    public String getLessonbyID(@PathVariable("lessonID") String lessonID) {
        return "첫 번째 레슨";
    }
}
