package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.InstructorVerificationDto;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.mapper.UserMapper;
import com.example.unisportserver.data.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    // TODO: 유저와 레슨 리턴할 때 id 포함하도록 Dto 수정 필요

    // 유저 생성이 Auth 쪽으로 가서 deprecated
//    public UserDto createUser(UserDto userDto) {
//        LocalDateTime now = LocalDateTime.now();
//        userDto.setCreatedAt(now);
//        userDto.setUpdatedAt(now);
//
//        UserEntity userEntity = userMapper.toEntity(userDto);
//        userRepository.save(userEntity);
//
//        return userMapper.toDto(userEntity);
//    }

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

        return "관리자 검토 후 연락드리겠습니다! \n\n" +
                "userId : " + id + ",\n\n" +
                "studentNumber : " + studentNumber;
    }
}
