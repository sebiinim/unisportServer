package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.ReviewCreateRequestDto;
import com.example.unisportserver.data.dto.ReviewResponseDto;
import com.example.unisportserver.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/review")
@Tag(name = "review-controller", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "리뷰 생성", description = "DTO에 유의하기")
    public ReviewResponseDto createReview(@RequestBody ReviewCreateRequestDto reviewCreateRequestDto) {
        return reviewService.saveReview(reviewCreateRequestDto);
    }

    @GetMapping(value = "{lessonId}")
    @Operation(summary = "레슨 리뷰 검색", description = "레슨의 모든 리뷰를 검색")
    public List<ReviewResponseDto> getAllReviewsByLessonId(@PathVariable Long lessonId) {
        return reviewService.getReviewsByLessonId(lessonId);
    }
}
