package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LoginDto;
import com.example.unisportserver.data.dto.RegisterDto;
import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.UserMapper;
import com.example.unisportserver.data.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto login(LoginDto loginDto){
        // loginId로 유저 검색
        UserEntity userEntity = userRepository.findByLoginId(loginDto.getLoginId()).
                orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 비밀번호가 일치할 때만 유저 정보 리턴하기
        if (encoder.matches(loginDto.getPassword(), userEntity.getPassword())) {
            userEntity.setPassword(null);
            return userMapper.toDto(userEntity);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "password error"
            );
        }
    }

    public UserDto register(RegisterDto registerDto){
        // registerDto 가지고 UserEntity 생성하기
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(registerDto.getPassword());

        UserEntity userEntity = UserEntity.builder()
                .loginId(registerDto.getLoginId())
                .password(hashedPassword)
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .university(registerDto.getUniversity())
                .studentNumber(registerDto.getStudentNumber())
                .isInstructor(false)
                .rating(0)
                .reviewCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(userEntity);

        return userMapper.toDto(userEntity);
    }
}
