package com.example.unisportserver.data.dao;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.repository.LessonRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonDAO {

    LessonRepository lessonRepository;


    public LessonEntity saveLesson(LessonEntity lessonEntity) {
        lessonRepository.save(lessonEntity);
        return lessonEntity;
    }

    public LessonEntity getLessonById(String id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElse(null);
        return lessonEntity;
    }

    public LessonEntity deleteLessonById(String id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElse(null);
        lessonRepository.delete(lessonEntity);

        return lessonEntity;
    }
}
