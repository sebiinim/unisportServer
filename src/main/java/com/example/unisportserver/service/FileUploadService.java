// src/main/java/com/example/unisportserver/service/GcsImageService.java
package com.example.unisportserver.service;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final Storage storage;

    @Value("${gcs.bucket-name}")
    private String bucketName;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            "image/webp",
            MediaType.IMAGE_GIF_VALUE
    );

    public String uploadLessonImage(Long lessonId, MultipartFile file) {
        if (file.isEmpty()) throw new IllegalArgumentException("빈 파일입니다.");
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }

        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (ext == null || ext.isBlank()) ext = "jpg";

        String objectName = "lessons/%d/%s.%s".formatted(
                lessonId, UUID.randomUUID(), ext.toLowerCase()
        );

        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName))
                .setContentType(contentType)
                .build();

        try {
            storage.create(blobInfo, file.getBytes());
            return objectName; // DB에는 objectName 저장 권장
        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }

    public URL generateV4GetSignedUrl(String objectName, Duration ttl) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();
        return storage.signUrl(blobInfo, ttl.toSeconds(), java.util.concurrent.TimeUnit.SECONDS,
                Storage.SignUrlOption.withV4Signature());
    }

    public void delete(String objectName) {
        storage.delete(BlobId.of(bucketName, objectName));
    }

    public String toPublicLikeUrl(String objectName) {
        // 버킷을 공개로 운영한다면 이 URL 형식으로 바로 접근 가능
        return "https://storage.googleapis.com/%s/%s".formatted(bucketName, objectName);
    }
}
