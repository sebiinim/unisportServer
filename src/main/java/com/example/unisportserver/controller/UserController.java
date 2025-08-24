package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.InstructorVerificationDto;
import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.repository.UserRepository;
import com.example.unisportserver.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "user-controller", description = "유저 API, 강사 등록 포함")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

//    @PostMapping
//    public UserDto createUser(@RequestBody UserDto request) {
//        return userService.createUser(request);
//    }

    @DeleteMapping("/{id}") 
    @Operation(summary = "유저 삭제", description = "유저 id를 이용하여 유저 삭제")
    public UserDto deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping()
    @Operation(summary = "모든 유저 검색", description = "모든 유저 리스트를 반환")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/instructor-verification/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "강사 등록", description = "유저 id, 강사 학번, 사진을 등록. 저장 등 구현은 나중에 하고 메시지 리턴")
    public String verifyInstructor(
        @PathVariable Long id,
        @RequestPart("studentNumber") String studentNumber,
        @RequestPart("photo") MultipartFile photo) {

        return userService.verifyInstructor(id, studentNumber, photo);
    }
}
