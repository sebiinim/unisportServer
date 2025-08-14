package com.example.unisportserver.repository;

import com.example.unisportserver.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> { }