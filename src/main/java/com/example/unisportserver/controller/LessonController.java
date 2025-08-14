package com.example.unisportserver.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @GetMapping(value = "/all")
    public String getAllLessons() {
        return "";
    }

    @GetMapping(value = "/search/{lessonID}")
    public String getLessonbyID(@PathVariable("lessonID") String lessonID) {
        return lessonID + "번째 레슨";
    }
}
