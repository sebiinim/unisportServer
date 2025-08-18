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

    // TODO : 빨래를 널고 와서 리뷰 GET을 만들어봅시다.
    @GetMapping
    public List<ReviewResponseDto> getAllReviews(@PathVariable Long lessonId) {
        return reviewService.getReviewsByLessonId(lessonId);
    }
}
