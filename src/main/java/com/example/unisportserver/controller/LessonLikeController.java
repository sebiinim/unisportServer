package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.service.LessonLikeService;
import com.example.unisportserver.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "lessonLike/{lessonId}")
@Tag(name = "lesson-like-controller", description = "레슨 좋아요 API")
public class LessonLikeController {

    private final LessonService lessonService;
    private final LessonLikeService lessonLikeService;

    @PostMapping
    @Operation(summary = "관심 레슨 등록", description = "레슨이나 유저의 id가 없는 경우 오류 발생, 이미 관심 레슨인 경우 메시지로 알려줌")
    public LessonLikeService.LikeResult like(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        return lessonLikeService.like(lessonId, userId);
    }

    @DeleteMapping
    @Operation(summary = "관심 레슨 등록 취소", description = "레슨이나 유저의 id가 없는 경우 오류 발생, 관심 레슨이 아닌 경우 메시지로 알려줌")
    public LessonLikeService.LikeResult unlike(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        return lessonLikeService.unlike(lessonId, userId);
    }

    @PostMapping("/toggle")
    @Operation(summary = "관심 레슨 토글", description = "관심 레슨 등록 상태를 바꿔줌, 이거 쓰는게 좋을듯")
    public LessonLikeService.LikeResult toggle(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        return lessonLikeService.toggle(lessonId, userId);
    }

    @GetMapping("/check")
    @Operation(summary = "관심 레슨에 등록된 레슨인지 확인")
    public boolean isLiked(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        return lessonLikeService.isliked(lessonId, userId);
    }

    @GetMapping("/count")
    @Operation(summary = "레슨을 관심 등록한 유저 수 확인", description = "수업 정보 보여줄 때 여기서 관심 유저 수 가져오기")
    public ResponseEntity<CountResponse> count(
            @PathVariable Long lessonId
    ) {
        long count = lessonLikeService.countLikes(lessonId);
        return ResponseEntity.ok(new CountResponse(count));
    }

    // --- 간단 DTOs ---
    public record LikeResponse(boolean liked, String message, long likeCount) {}
    public record CountResponse(long likeCount) {}
}
