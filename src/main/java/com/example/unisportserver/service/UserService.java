package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.UserMapper;
import com.example.unisportserver.data.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    // TODO: 정보 수정 API도 필요

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // id로 유저 삭제
    public UserDto deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with id %d not found", id)));
        UserDto deletedUserDto = userMapper.toDto(userEntity);

        userRepository.deleteById(id);

        return deletedUserDto;
    }

    // 모든 유저 검색
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        return userMapper.toDtoList(userEntities);
    }

    // id로 유저 검색
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with id %d not found", id))
        );
        return userMapper.toDto(userEntity);
    }

    // 강사 인증 요청
    public String verifyInstructor(Long id, String studentNumber, MultipartFile photo) {

        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("userId" + id + "not found"));

        user.setIsInstructor(Boolean.TRUE);

        userRepository.save(user);

        return "인증되었습니다!";
    }
}
