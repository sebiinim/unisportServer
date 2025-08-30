package com.example.unisportserver.data.repository;

import com.example.unisportserver.data.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    SubscriptionEntity findByUserId(Long userId);
}
