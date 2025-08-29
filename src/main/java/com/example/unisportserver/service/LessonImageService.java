// src/main/java/com/example/unisportserver/service/LessonImageService.java
package com.example.unisportserver.service;

import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LessonImageService {

    private final LessonRepository lessonRepository;
    private final FileUploadService gcsImageService;

    @Transactional
    public String upload(Long lessonId, MultipartFile image) {
        LessonEntity lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("레슨을 찾을 수 없습니다. id=" + lessonId));

        // 기존 이미지가 있다면 정리 (선택)
        if (lesson.getImagePath() != null) {
            gcsImageService.delete(lesson.getImagePath());
        }

        String objectName = gcsImageService.uploadLessonImage(lessonId, image);
        lesson.setImagePath(objectName);
        // JPA flush 시점에 자동 저장
        return objectName;
    }

    @Transactional(readOnly = true)
    public String getSignedUrl(Long lessonId) {
        LessonEntity lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("레슨을 찾을 수 없습니다. id=" + lessonId));

        if (lesson.getImagePath() == null) {
            throw new IllegalStateException("등록된 이미지가 없습니다.");
        }
        return gcsImageService.generateV4GetSignedUrl(lesson.getImagePath(), Duration.ofMinutes(10))
                .toString();
    }
}
