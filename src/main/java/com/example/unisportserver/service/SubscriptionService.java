package com.example.unisportserver.service;

import com.example.unisportserver.data.entity.SubscriptionEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.repository.SubscriptionRepository;
import com.example.unisportserver.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;


    // 구독 등록
    public SubscriptionEntity startSubscription(Long userId, LocalDate startDate, LocalDate endDate) {

        // 유저 검증
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User id:" + userId + "not found")
        );

        // 구독 등록
        SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .user(userEntity).startDate(startDate).endDate(endDate).build();

        subscriptionRepository.save(subscriptionEntity);

        return subscriptionEntity;
    }


    // 구독 조회
    public boolean checkSubscription(Long userId) {

        // 유저 검증
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User id:" + userId + "not found")
        );

        // 구독 조회
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByUserId(userId);
        if (subscriptionEntity.getStartDate().isBefore(LocalDate.now()) && subscriptionEntity.getEndDate().isAfter(LocalDate.now())) {
            return true;
        } else {
            return false;
        }
    }


    // 구독 삭제
    public SubscriptionEntity endSubscription(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User id:" + userId + "not found")
        );

        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByUserId(userId);
        subscriptionRepository.delete(subscriptionEntity);

        return subscriptionEntity;
    }


}
