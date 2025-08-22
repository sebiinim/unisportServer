package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LoginDto;
import com.example.unisportserver.data.dto.RegisterDto;
import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "auth-controller", description = "로그인, 회원가입 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    @Operation(summary = "로그인", description = "id, pw 입력해 로그인, 유저 테이블에서 비번 해시값을 비교")
    public UserDto login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

    @PostMapping(value = "/register")
    @Operation(summary = "회원가입", description = "id, pw, 이름, 이메일, 대학명, 학번으로 회원가입")
    public UserDto register(@RequestBody RegisterDto registerDto){
        return authService.register(registerDto);
    }

}
