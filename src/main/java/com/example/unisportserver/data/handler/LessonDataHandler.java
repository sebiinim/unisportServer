package com.example.unisportserver.data.handler;


import com.example.unisportserver.data.dao.LessonDAO;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LessonDataHandler {

    LessonDAO lessonDAO;

    public LessonEntity getLessonById(String id){
        return lessonDAO.getLessonById(id);
    }

    public LessonEntity saveLessonEntity(String id, String title, String description, String sport){
        LessonEntity lessonEntity = new LessonEntity(id, title, description, sport);

        return lessonDAO.saveLesson(lessonEntity);
    }

    public void deleteLessonById(String id){
        lessonDAO.deleteLessonById(id);
    }


}

