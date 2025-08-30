// src/main/java/com/example/unisportserver/controller/LessonImageController.java
package com.example.unisportserver.controller;

import com.example.unisportserver.service.LessonImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
@Tag(name = "lesson-image-controller", description = "레슨의 사진 관련 api")
public class LessonImageController {

    private final LessonImageService lessonImageService;

    // 업로드
    @PostMapping(value = "/{lessonId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "레슨 사진 업로드", description = "레슨 id랑 사진 파일 업로드하면 레슨에 사진이 등록됨")
    public ResponseEntity<?> upload(
            @PathVariable Long lessonId,
            @RequestParam("image") MultipartFile image
    ) {
        String objectName = lessonImageService.upload(lessonId, image);
        return ResponseEntity.ok().body(new UploadResponse(objectName));
    }

    // 보기 - 1) 서명URL을 JSON으로 내려주기
    @GetMapping("/{lessonId}/image-url")
    @Operation(summary = "레슨 사진 불러오기1 (url)", description = "사진의 url을 줍니다.")
    public ResponseEntity<?> getImageUrl(@PathVariable Long lessonId) {
        String signedUrl = lessonImageService.getSignedUrl(lessonId);
        return ResponseEntity.ok().body(new UrlResponse(signedUrl));
    }

    // 보기 - 2) 서명URL로 302 리다이렉트(프론트가 이미지 src에 바로 이 엔드포인트를 써도 됨)
    @GetMapping("/{lessonId}/image")
    @Operation(summary = "레슨 사진 불러오기2 (image)", description = "사진을 바로 불러옵니다.")
    public ResponseEntity<?> redirectToImage(@PathVariable Long lessonId) {
        String signedUrl = lessonImageService.getSignedUrl(lessonId);
        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, signedUrl)
                .build();
    }

    // 간단 응답 DTO
    private record UploadResponse(String objectName) {
    }

    private record UrlResponse(String url) {
    }
}
