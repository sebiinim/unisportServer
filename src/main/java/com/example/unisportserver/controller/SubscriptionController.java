package com.example.unisportserver.controller;

import com.example.unisportserver.data.entity.SubscriptionEntity;
import com.example.unisportserver.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Tag(name = "subscription-controllor", description = "구독 API")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(summary = "구독 생성", description = "userId, 시작 날짜, 끝 날짜로 구독 생성")
    public SubscriptionEntity startSubscription(@RequestBody Long userId, LocalDate startDate, LocalDate endDate) {

        return subscriptionService.startSubscription(userId, startDate, endDate);
    }

    @GetMapping
    @Operation(summary = "구독 확인", description = "userId로 현재 구독 상태인지 확인")
    public boolean checkSubscription(@RequestBody Long userId) {

        return subscriptionService.checkSubscription(userId);
    }

    @DeleteMapping
    @Operation(summary = "구독 삭제", description = "유저가 구독을 취소")
    public SubscriptionEntity endSubscription(@RequestBody Long userId) {

        return subscriptionService.endSubscription(userId);
    }
}
