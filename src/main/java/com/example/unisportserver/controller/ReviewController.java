package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.ReviewCreateRequestDto;
import com.example.unisportserver.data.dto.ReviewResponseDto;
import com.example.unisportserver.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponseDto createReview(@RequestBody ReviewCreateRequestDto reviewCreateRequestDto) {
        return reviewService.saveReview(reviewCreateRequestDto);
    }

    @GetMapping(value = "{lessonId}")
    public List<ReviewResponseDto> getAllReviewsByLessonId(@PathVariable Long lessonId) {
        return reviewService.getReviewsByLessonId(lessonId);
    }
}
