package com.example.unisportserver.service;

import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.mapper.UserMapper;
import com.example.unisportserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // 유저 생성
    public UserDto createUser(UserDto userDto) {
        LocalDateTime now = LocalDateTime.now();
        userDto.setCreatedAt(now);
        userDto.setUpdatedAt(now);

        UserEntity userEntity = userMapper.toEntity(userDto);
        userRepository.save(userEntity);

        return userMapper.toDto(userEntity);
    }

    // id로 유저 삭제
    public UserDto deleteUser(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id : " + id + " user not found"));

        UserDto deletedUserDto = userMapper.toDto(userEntity);

        userRepository.deleteById(id);

        return deletedUserDto;
    }

    // 모든 유저 검색
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        return userMapper.toDtoList(userEntities);
    }
}
