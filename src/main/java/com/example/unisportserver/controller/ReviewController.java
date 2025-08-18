package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.ReviewCreateRequestDto;
import com.example.unisportserver.data.dto.ReviewResponseDto;
import com.example.unisportserver.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
