package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.handler.LessonDataHandler;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    LessonDataHandler lessonDataHandler;

    public LessonDto getLessonById(String id){
        return null;
    }

    public LessonDto saveLesson(String id, String title, String description, String sport) {
        LessonEntity lessonEntity = lessonDataHandler.saveLessonEntity(id, title, description, sport);

        LessonDto lessonDto = LessonDto.builder().
                id(lessonEntity.getId()).title(lessonEntity.getTitle()).description(lessonEntity.getDescription())
                .sport(lessonEntity.getSport()).build();

        return lessonDto;
    }

    public LessonDto deleteLessonById(String id){
        return null;
    }
}
