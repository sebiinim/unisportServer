package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LoginDto;
import com.example.unisportserver.data.dto.RegisterDto;
import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.UserMapper;
import com.example.unisportserver.data.repository.UserRepository;
import com.example.unisportserver.util.Normalizer;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Normalizer normalizer;

    public UserDto login(LoginDto loginDto){
        String loginId = normalizer.Normalize(loginDto.getLoginId());
        String password = normalizer.Normalize(loginDto.getPassword());

        // loginId로 유저 검색
        UserEntity userEntity = userRepository.findByLoginId(loginId).
                orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 비밀번호가 일치할 때만 유저 정보 리턴하기
        if (encoder.matches(password, normalizer.Normalize(userEntity.getPassword()))) {
            userEntity.setPassword(null);
            return userMapper.toDto(userEntity);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "password error"
            );
        }
    }

    public UserDto register(RegisterDto registerDto){

        // 항상 소문자, 공백 없음으로 통일
        String loginId = normalizer.Normalize(registerDto.getLoginId());

        if(userRepository.existsByLoginId(loginId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다.");
        }

        // registerDto 가지고 UserEntity 생성하기
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(registerDto.getPassword());

        try {
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
        } catch (DataIntegrityViolationException e) {
            // DB 유니크 제약 충돌 처리
            throw new RuntimeException("이미 존재하는 아이디입니다: " + loginId, e);
        }
    }

    // DB에 중복되는 loginId가 있는지 확인, T/F return
    public boolean checkDuplicateId(String loginId){

        return userRepository.findByLoginId(loginId).isPresent();
    }
}
