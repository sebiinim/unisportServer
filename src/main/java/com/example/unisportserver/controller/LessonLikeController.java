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
    @Operation(summary = "좋아요", description = "레슨이나 유저의 id가 없는 경우 오류 발생, 좋아요를 이미 누른 경우 메시지로 알려줌")
    public ResponseEntity<?> like(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        LessonLikeService.LikeResult r = lessonLikeService.like(lessonId, userId);
        long count = lessonLikeService.countLikes(lessonId);

        return switch(r) {
            case CREATED -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(new LikeResponse(true, "좋아요를 눌렀습니다.", count));
            case ALREADY_EXISTS ->  ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new LikeResponse(false, "좋아요를 이미 눌렀습니다.", count));
            default -> ResponseEntity.internalServerError().build();
        };
    }

    @DeleteMapping
    @Operation(summary = "좋아요 취소", description = "레슨이나 유저의 id가 없는 경우 오류 발생, 좋아요가 없는 경우 메시지로 알려줌")
    public ResponseEntity<?> unlike(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        LessonLikeService.LikeResult r = lessonLikeService.unlike(lessonId, userId);
        long count = lessonLikeService.countLikes(lessonId);

        return switch(r) {
            case DELETED -> ResponseEntity.ok(new LikeResponse(false, "좋아요를 취소했습니다.", count));
            case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LikeResponse(false, "좋아요 상태가 아닙니다.", count));
            default -> ResponseEntity.internalServerError().build();
        };
    }

    @PostMapping("/toggle")
    @Operation(summary = "좋아요 토글", description = "좋아요 상태를 바꿔줌")
    public ResponseEntity<ToggleLikeResponse> toggle(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        boolean nowLiked = lessonLikeService.toggle(lessonId, userId);
        long count = lessonLikeService.countLikes(lessonId);
        return ResponseEntity.ok(new ToggleLikeResponse(nowLiked, count));
    }

    @GetMapping("/check")
    @Operation(summary = "좋아요를 눌렀는지 체크")
    public ResponseEntity<IsLikedResponse> isLiked(
            @PathVariable Long lessonId,
            @RequestParam Long userId
    ) {
        boolean liked = lessonLikeService.isliked(lessonId, userId);
        return ResponseEntity.ok(new IsLikedResponse(liked));
    }

    @GetMapping("/count")
    @Operation(summary = "좋아요 개수 확인", description = "수업 정보 보여줄 때 여기서 좋아요 개수 가져오기")
    public ResponseEntity<CountResponse> count(
            @PathVariable Long lessonId
    ) {
        long count = lessonLikeService.countLikes(lessonId);
        return ResponseEntity.ok(new CountResponse(count));
    }

    // --- 간단 DTOs ---
    public record LikeResponse(boolean liked, String message, long likeCount) {}
    public record ToggleLikeResponse(boolean liked, long likeCount) {}
    public record IsLikedResponse(boolean liked) {}
    public record CountResponse(long likeCount) {}
}
